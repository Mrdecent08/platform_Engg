#!/bin/bash

# Variables
alertName="$1"
podName="$2"
namespace="${3:-default}"
read -a resources <<< "$4"
replica_set=$(kubectl get pod $podName -n $namespace -o jsonpath='{.metadata.ownerReferences[0].name}')
deployment_name=$( kubectl get replicaset $replica_set -n $namespace -o jsonpath='{.metadata.ownerReferences[0].name}')


# Function to get total and available CPU and memory
get_cluster_resources() {
  kubectl top nodes --no-headers | awk '
    {
      total_cpu += $2;
      used_cpu += $3;
      total_mem += $4;
      used_mem += $5;
    }
    END {
      available_cpu = total_cpu - used_cpu;
      available_mem = total_mem - used_mem;
      print available_cpu " " available_mem
    }
  '
}


# Function to update the deployment with new resource requests and limits
update_deployment_resources() {
  local new_cpu_request=$1
  local new_mem_request=$2
  local new_cpu_limit=$3
  local new_mem_limit=$4

  if [[ ${#resources[@]} -eq 2 ]]; then
    echo "Modifying cpu and memory resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/cpu", "value": "'$new_cpu_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/memory", "value": "'$new_mem_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/cpu", "value": "'$new_cpu_limit'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/memory", "value": "'$new_mem_limit'"}]'
  elif [[ "${resources[0]}" == "cpu" ]] && [[ "$current_mem_request" == "null" ]]; then
    echo "Modifying cpu resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/cpu", "value": "'$new_cpu_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/cpu", "value": "'$new_cpu_limit'"}]'
  elif [[ "${resources[0]}" == "cpu" ]] && [[ "$current_mem_request" != "null" ]]; then
    echo "Modifying cpu resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/cpu", "value": "'$new_cpu_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/memory", "value": "'$current_mem_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/cpu", "value": "'$new_cpu_limit'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/memory", "value": "'$current_mem_limit'"}]'
  elif [[ "${resources[0]}" == "memory" ]] && [[ "$current_cpu_request" == "null" ]]; then
    echo "Modifying memory resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/memory", "value": "'$new_mem_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/memory", "value": "'$new_mem_limit'"}]'
  else
    echo "Modifying memory resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/cpu", "value": "'$current_cpu_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/memory", "value": "'$new_mem_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/cpu", "value": "'$current_cpu_limit'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/memory", "value": "'$new_mem_limit'"}]'
  fi
}

# Get the available cluster resources
read available_cpu available_mem <<< $(get_cluster_resources)

# Convert available memory from Mi to Gi for easier comparison
available_mem_gi=$(echo "scale=2; $available_mem / 1024" | bc)

# Get current deployment resource requests and limits
echo "Resources: $resources"
current_resources=$(kubectl get deployment $deployment_name -n $namespace -o jsonpath='{.spec.template.spec.containers[0].resources}')
current_cpu_request=$(echo $current_resources | jq -r '.requests.cpu')
current_mem_request=$(echo $current_resources | jq -r '.requests.memory')
current_cpu_limit=$(echo $current_resources | jq -r '.limits.cpu')
current_mem_limit=$(echo $current_resources | jq -r '.limits.memory')

echo "Available CPU: $available_cpu cores"
echo "Available Memory: $available_mem Mi "
echo "Current CPU Request: $current_cpu_request"
echo "Current Memory Request: $current_mem_request"
echo "Current CPU Limit: $current_cpu_limit"
echo "Current Memory Limit: $current_mem_limit"

# New resource requests and limits based on available resources (example: setting requests to 10% of available resources and limits to 20%)
# new_cpu_request=$(echo "scale=2; $available_cpu * 0.8" | bc)
# new_mem_request=$(echo "scale=2; $available_mem_gi * 0.8" | bc)"Mi"
# new_cpu_limit=$(echo "scale=2; $available_cpu " | bc)
# new_mem_limit=$(echo "scale=2; $available_mem_gi " | bc)"Mi"

new_cpu_request="200m"
new_mem_request="128Mi"
new_cpu_limit="500m"
new_mem_limit="512Mi"



# Update the deployment with new resource requests and limits
update_deployment_resources $new_cpu_request $new_mem_request $new_cpu_limit $new_mem_limit

echo "Deployment resources updated to:"
echo "New CPU Request: $new_cpu_request cores"
echo "New Memory Request: $new_mem_request"
echo "New CPU Limit: $new_cpu_limit cores"
echo "New Memory Limit: $new_mem_limit"
