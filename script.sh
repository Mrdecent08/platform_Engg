#!/bin/bash

alertName="$1"
podName="$2"
namespace="${3:-default}"
gitlab_username="srinivas"
gitlab_password="Soulcynic@08"
encoded_gitlab_password=$(python3 -c "import urllib.parse; print(urllib.parse.quote('''$gitlab_password'''))")
label_mismatch_regex=".*nodes are available.*didn't match Pod's node affinity/selector.*"
secret_not_found=".*Failed to pull image.*401 Unauthorized.*"
image_not_found=".*Back-off pulling image.*"
volume_not_found=".*persistentvolumeclaim.*not found.*"
remediation=""

suggest_remediations=$(curl -XGET "http://10.63.16.153:30128/retrieveAlertByName?alertName=$(echo "$alertName" | sed 's/ /%20/g')" -H "accept: */*")
JIRA_URL="https://abhishek-v.atlassian.net/rest/api/2/issue"
AUTH_HEADER="Basic YWJoaXNoZWsudmVsaWNoYWxhQHRjcy5jb206QVRBVFQzeEZmR0YwLVBRamJoaXUtUWdMTUptcUZ0dUhlc2NrZWN6eHUycEo2Y0FsaTk3X3gzWFlWSmQzeHNzNjNtSUFWMVBzOE1aM0lQbi1TU1dwaTMwaDlLbzd1QTNOMmszSEhnMFFIUlltT1dXMnJlOVNONUQ3M0xaV3lxb2dzbGdzUUMwcWQzSTBFTVh4YTRwTXRiZTh1ajB5M0cyc250M0hUZllJUDgwYUVFTWxrU0FBenFjPTcyNTYxNzFE"


# Function to get pod information
get_pod_info() {
  kubectl get pods -n "$namespace" | grep "$podName" | awk '{ print $1 }' | head -1
}

# Function to get pod logs for Deployment Replica Mismatch
get_pod_logs_conditions() {
  kubectl get pods "$1" -n "$namespace" -o jsonpath='{.status.conditions[0].message}'
}


get_deployment_logs() {
  kubectl get deploy "$1" -n "$namespace" -o jsonpath='{.status.conditions[0].message}'
}


# Function to get pod logs for Kubernetes Pod Not Healthy
get_pod_logs_container_status() {
  kubectl get pods "$podName" -n "$namespace" -o jsonpath='{.status.containerStatuses[0].state.waiting.message}'
}

# Function to clone the playbook from GitLab
clone_playbook() {
  git clone --depth 1 "http://${gitlab_username}:${encoded_gitlab_password}@10.63.32.87/platformengineering/microservices.git" -b scripts scripts
}

# Function to execute remediation script
execute_remediation() {
  clone_playbook
  bash "$1" "$alertName" "$podName" "$namespace"
}

# Function to execute remediation script
execute_remediation_resources() {
  clone_playbook
  bash "$1" "$alertName" "$podName" "$namespace"
}


# Function to raise a ticket
raise_ticket() {
  local summary="$1"
  local description="$2"
  local JSON_PAYLOAD=$(cat <<EOF
{
  "fields": {
    "project": {
      "key": "SH"
    },
    "summary": "$summary",
    "description": "$description",
    "issuetype": {
      "name": "Task"
    }
  }
}
EOF
)

  curl -X POST \
  "$JIRA_URL" \
  -H "Content-Type: application/json" \
  -H "Authorization: $AUTH_HEADER" \
  -d "$JSON_PAYLOAD"
}

# Main script logic
if [[ -z "$suggest_remediations" ]]; then
  msg="No Data Found for $alertName"
  raise_ticket $alertName $msg
  exit 1
fi

if [[ "$alertName" == "Deployment Replica Mismatch" ]]; then
  pod_info=$(get_pod_info)

  if [[ -z "$pod_info" ]]; then
    echo "No pod found matching the name $podName in namespace $namespace"
    exit 1
  fi

  pod_logs=$(get_pod_logs_conditions "$pod_info")

  for row in $(echo "${suggest_remediations}" | jq -r '.[] | @base64'); do
    _jq() {
      echo "${row}" | base64 --decode | jq -r "${1}"
    }

    alertname=$(_jq '.alertname')
    category=$(_jq '.category')
    logSignature=$(_jq '.logSignature')
    remediation=$(_jq '.remediation')

    if [[ "$pod_logs" =~ $logSignature ]]; then
      echo "Matching logSignature found. Initiating remediation..."
      clone_playbook
      execute_remediation "$remediation"
      exit 0
    fi
  done

elif [[ "$alertName" == "Kubernetes Pod Not Healthy" ]]; then
  pod_logs=$(get_pod_logs_container_status)

  for row in $(echo "${suggest_remediations}" | jq -r '.[] | @base64'); do
    _jq() {
      echo "${row}" | base64 --decode | jq -r "${1}"
    }

    alertname=$(_jq '.alertname')
    category=$(_jq '.category')
    logSignature=$(_jq '.logSignature')
    remediation=$(_jq '.remediation')

    if [[ "$pod_logs" =~ $logSignature ]]; then
      echo "Matching logSignature found. Initiating remediation..."
      clone_playbook
      execute_remediation "$remediation"
      exit 0
    fi
  done
  # if [[ "$pod_logs" =~ $image_not_found ]]; then
  #   image=$(kubectl get pods "$podName" -n "$namespace" -o jsonpath='{.spec.containers[0].image}')
  #   msg="Image $image not found in the registry"
  #   raise_ticket "$alertName" "$msg"
  #   remediation="image-not-found.sh"
  # fi

  # if [[ "$pod_logs" =~ $secret_not_found ]]; then
  #   remediation="secret_not_found.sh"
  # fi

  INIT_STATUS=$(kubectl get pod "$podName" -n "$namespace" -o jsonpath='{.status.initContainerStatuses[*].state.waiting.message}')
  if [[ "$INIT_STATUS" == *"CrashLoopBackOff"* ]]; then
    msg="Pod $podName in $namespace namespace has failed init container, restarting..."
    kubectl delete pod "$podName" -n "$namespace"
    raise_ticket "$alertName" "$msg"
  fi

  if [[ "$(get_pod_logs_conditions "$podName")" =~ $volume_not_found ]]; then
    pvc_name=$(kubectl get pod "$podName" -n "$namespace" -o jsonpath='{.spec.volumes[0].persistentVolumeClaim.claimName}')
    available_pvc=()
    echo "PVC volume $pvc_name in $namespace namespace is not available"
    while IFS= read -r line; do
        available_pvc+=("$line")
    done < <(kubectl get pvc -n "$namespace" --no-headers | awk '{print $1}')

    msg="PVC volume $pvc_name in $namespace namespace is not available, available PVCs in the $namespace are: "
    for pvc in "${available_pvc[@]}"; do
        msg+="$pvc "
    done
    raise_ticket "$alertName" "$msg"
  fi

  pod_logs=$(get_pod_logs_conditions "$podName")
  if [[ "$pod_logs" =~ (Insufficient\ cpu) && "$pod_logs" =~ (Insufficient\ memory) ]]; then
    resources='"cpu" "memory"'
    echo "Insufficient CPU and memory"
    execute_remediation_resources "$remediation" "$resources"
    msg="Pod $podName in $namespace namespace has Insufficient CPU and memory resources"
    raise_ticket "$alertName" "$msg"
  elif [[ "$pod_logs" =~ (Insufficient\ cpu) ]]; then
    resources="cpu"
    echo "Insufficient CPU"
    execute_remediation_resources "$remediation" "$resources"
    msg="Pod $podName in $namespace namespace has Insufficient CPU resources"
    raise_ticket "$alertName" "$msg"
  elif [[ "$pod_logs" =~ (Insufficient\ memory) ]]; then
    resources="memory"
    echo "Insufficient memory"
    execute_remediation_resources "$remediation" "$resources"
    msg="Pod $podName in $namespace namespace has Insufficient memory resources"
    raise_ticket "$alertName" "$msg"
  fi

elif [[ "$alertName" == "Pod Using Max memory" ]]; then
  remediation="increase_memory.sh"
elif [[ "$alertName" == "Pod Using Max CPU" ]]; then
  remediation="increase_cpu.sh"
elif [[ "$alertName" == "Pods stuck at Terminating" ]]; then
  remediation="pod_stuck_terminating.sh"
elif [[ "$alertName" == "Kubernetes Pod CrashLooping" ]]; then
  kubectl get pod "$podName" -n "$namespace" -o yaml > pod.yaml
  if ! kubectl apply -f pod.yaml --dry-run=client > /dev/null; then
    echo "Pod configuration file contains errors"
    exit 1
  fi
  remediation="crashloop-backoff.sh"
elif [[ "$alertName" == "Kubernetes pod with multiple restarts" ]]; then
  msg="Pod $podName in $namespace namespace has been restarting multiple times"
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "Pod count per node high" ]]; then
  curr_pods=$(kubectl get pods --all-namespaces -o wide --field-selector spec.nodeName="$podName" | wc -l)
  max_pods=$(kubectl get nodes --field-selector metadata.name="$podName" -o jsonpath='{.status.capacity.pods}')
  msg="Node $podName has $curr_pods running on it and the max capacity is $max_pods pods"
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "Job Failed" ]]; then
  msg="Job $podName in $namespace namespace has Failed."
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "Pod with rapid increase in memory usage" ]]; then
  msg="Pod $podName in $namespace namespace might have a memory leak."
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "Hpa running at Max replicas" ]]; then
  remediation="hpa-replicas.sh"
elif [[ "$alertName" == "Create Container Config Error" ]]; then
  msg=$(kubectl get pod "$podName" -n "$namespace" -o jsonpath='{.status.containerStatuses[0].state.waiting.message}')
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "Cluster Memory Usage" ]]; then
  msg="Cluster Memory has reached 80%"
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "PVC in pending state" ]]; then
  msg="PVC $podName in $namespace namespace is in pending state because "
  msg+=$(kubectl get events --field-selector involvedObject.kind=PersistentVolumeClaim,involvedObject.name="$podName" -o json | jq -r '.items | sort_by(.lastTimestamp) | last | .message')
  raise_ticket "$alertName" "$msg"
elif [[ "$alertName" == "All Replicas on Same Node" ]]; then
  remediation="replicaset-on-samenode.sh"

elif [[ "${alertName,,}" =~ "deployment"  ]]; then

  pod_info=$(get_pod_info)

  if [[ -z "$pod_info" ]]; then
    echo "No pod found matching the name $podName in namespace $namespace"
    exit 1
  fi

  logs=$(get_pod_logs_conditions "$pod_info")

  found="false"
  for row in $(echo "${suggest_remediations}" | jq -r '.[] | @base64'); do
    _jq() {
      echo "${row}" | base64 --decode | jq -r "${1}"
    }

    alertname=$(_jq '.alertname')
    category=$(_jq '.category')
    logSignature=$(_jq '.logSignature')
    remediation=$(_jq '.remediation')

    if [[ "$logs" =~ $logSignature ]]; then
      found="true"
      echo "Matching logSignature found. Initiating remediation..."
      clone_playbook
      execute_remediation "$remediation"
      exit 0
    fi
  done
  if [[ $found != "true" ]]; then
    msg="Deployment $podName has an issue in $namespace namespace."
    raise_ticket $alertName 
  fi

elif [[ "${alertName,,}" =~ "pod" || "${alertName,,}" =~ "job"  ]]; then

  logs=$(get_pod_logs_conditions "$podName")
  found="false"
  for row in $(echo "${suggest_remediations}" | jq -r '.[] | @base64'); do
    _jq() {
      echo "${row}" | base64 --decode | jq -r "${1}"
    }

    alertname=$(_jq '.alertname')
    category=$(_jq '.category')
    logSignature=$(_jq '.logSignature')
    remediation=$(_jq '.remediation')

    if [[ "$logs" =~ $logSignature ]]; then
      found="true"
      echo "Matching logSignature found. Initiating remediation..."
      clone_playbook
      execute_remediation "$remediation"
      exit 0
    fi
    
  done
  if [[ $found != "true" ]]; then
      msg="Pod $podName has an issue in $namespace namespace.."
      raise_ticket $alertName 
  fi
  exit 1
else
  msg="There is issue with $podName in $namespace namespace."
  raise_ticket $alertName $msg
fi

