#!/bin/bash

alertName="$1"
podName="$2"
namespace="${3:-default}"
gitlab_username="srinivas"
gitlab_password="Soulcynic@08"
encoded_gitlab_password=$(python3 -c "import urllib.parse; print(urllib.parse.quote('''$gitlab_password'''))")
label_mismatch_regex=".*nodes are available.*didn't match Pod's node affinity/selector.*"
image_not_found=".*Back-off pulling image.*"
volume_not_found=".*persistentvolumeclaim.*not found.*"
raise_ticket=".*Raise a Ticket.*"
credentials_not_found=""
remediation=""

# Function to get pod information
get_pod_info() {
  kubectl get pods -n "$namespace" | grep "$podName" | awk '{ print $1 }' | head -1
}

# Function to get pod logs for Deployment Replica Mismatch
get_pod_logs_conditions() {
  kubectl get pods "$1" -n "$namespace" -o jsonpath='{.status.conditions[0].message}'
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
  bash "$1" "$alertName" "$podName" "$namespace" "$2"
}

# Main script logic
if [[ "$alertName" == "Deployment Replica Mismatch" ]]; then
  pod_info=$(get_pod_info)

  if [[ -z "$pod_info" ]]; then
    echo "No pod found matching the name $podName in namespace $namespace"
    exit 1
  fi

  pod_logs=$(get_pod_logs_conditions "$pod_info")

  if [[ "$pod_logs" =~ $label_mismatch_regex ]]; then
    remediation="replica-mismatch.sh"
  fi

elif [[ "$alertName" == "Kubernetes Pod Not Healthy" ]]; then
  pod_logs=$(get_pod_logs_container_status)

  if [[ "$pod_logs" =~ $image_not_found ]]; then
    remediation="image-not-found.sh"
  fi
  
  if [[ "$(get_pod_logs_conditions $podName)" =~ $volume_not_found ]]; then
    pvc_name=$(kubectl get pod $podName -n $namespace -o jsonpath='{.spec.volumes[0].persistentVolumeClaim.claimName}')
    available_pvc=()
    echo "PVC volume $pvc_name is not available"
    while IFS= read -r line; do
        available_pvc+=("$line")
    done < <(kubectl get pvc -n "$namespace" --no-headers | awk '{print$1}')

    echo "PVC available in the $namespace are : "
    for pvc in "${available_pvc[@]}"; do
        echo "$pvc"
    done
    #########################################################################
    remediation="Raise a Ticket"
    #########################################################################
  fi

  pod_logs=$(get_pod_logs_conditions "$podName")
  if [[ "$pod_logs" =~ (Insufficient\ cpu) && "$pod_logs" =~ (Insufficient\ memory) ]]; then
    resources='"cpu" "memory"'
    echo " Insufficient CPU and memory"
    remediation="resources.sh"
  elif [[ "$pod_logs" =~ (Insufficient\ cpu) ]]; then
    resources="cpu"
    echo " Insufficient CPU"
    remediation="resources.sh"
  elif [[ "$pod_logs" =~ (Insufficient\ memory) ]]; then
    resources="memory"
    echo " Insufficient memory"
    remediation="resources.sh"
  fi
  
  
elif [[ "$alertName" == "Pod Using Max memory" ]]; then
  remediation="increase_memory.sh"
elif [[ "$alertName" == "Pod Using Max CPU" ]]; then
  remediation="increase_cpu.sh"
elif [[ "$alertName" == "Pods stuck at Terminating" ]]; then
  remediation="pod_stuck_terminating.sh"
elif [[ "$alertName" == "Kubernetes Pod CrashLooping" ]]; then
  kubectl get pod $podName -n $namespace -o yaml > pod.yaml
  if ! kubectl apply -f pod.yaml --dry-run > /dev/null; then  
    echo "Pod configuration file containers errors"
    exit 1
  fi
  remediation="crashloop-backoff.sh"
elif [[ "$alertName" == "Kubernetes pod with multiple restarts" ]]; then
  remediation="increase-replicas.sh"
elif [[ "$alertName" == "Pod count per node high" ]]; then
  curr_pods=$( kubectl get pods --all-namespaces -o wide --field-selector spec.nodeName="$podName" | wc -l)
  max_pods=$(kubectl get nodes --field-selector metadata.name="$podName" -o jsonpath='{.items[0].status.capacity.pods}')
  msg="Node $podName has $curr_pods running on it and the max capacity is $max_pods pods"
  #########################################################################
  remediation="Raise a Ticket $msg"
  #########################################################################  
elif [[ "$alertName" == "Job Failed" ]]; then
  #########################################################################
  remediation="Raise a Ticket"
  #########################################################################
elif [[ "$alertName" == "Pod with rapid increase in memory usage" ]]; then
  #########################################################################
  remediation="Raise a Ticket"
  #########################################################################
elif [[ "$alertName" == "hpa running at Max replicas" ]]; then
  remediation="hpa-replicas.sh"
elif [[ "$alertName" == "Create Container Config Error" ]]; then
  msg=$(kubectl get pod $podName -n $namespace -o jsonpath='{.status.containerStatuses[0].state.waiting.message}')
  #########################################################################
  remediation="Raise a Ticket $msg"
  #########################################################################  
elif [[ "$alertName" == "Cluster Memory Usage" ]]; then
    #########################################################################
  remediation="Raise a Ticket"
  #########################################################################  
elif [[ "$alertName" == "PVC in pending state" ]]; then
  msg=$(kubectl get events --field-selector involvedObject.kind=PersistentVolumeClaim,involvedObject.name=demo-pvc -o json | jq -r '.items | sort_by(.lastTimestamp) | last | .message')
  #########################################################################
  remediation="Raise a Ticket"
  #########################################################################
elif [[ "$alertName" == "All Replicas on Same Node" ]]; then
  remediation="replicaset-on-samenode.sh"
else
  echo "Alert name does not match any known conditions"
  exit 1
fi


if [[ "$remediation" =~ $raise_ticket ]]; then
  echo "$remediation"
elif [[ -n "$remediation" ]]; then
  echo "$remediation"
  clone_playbook
  execute_remediation "$remediation" "$resources"
  echo "Remediation playbook executed: $remediation"
else
  echo "No remediation required"
fi
