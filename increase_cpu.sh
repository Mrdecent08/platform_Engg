#!/bin/bash

# Variables
alertName="$1"
podName="$2"
namespace="${3:-default}"
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

# Function to increase resource value by a given percentage
increase_resource() {
  local current_resource=$1
  local increase_percentage=$2

  # Extract numeric part and unit part
  local numeric_value=$(echo $current_resource | grep -oP '^\d+')
  local unit=$(echo $current_resource | grep -oP '[a-zA-Z]+$')

  # Calculate new value by increasing the given percentage
  local new_numeric_value=$(echo "scale=2; $numeric_value * (1 + $increase_percentage / 100)" | bc)

  # Combine new numeric value with unit
  local new_resource="${new_numeric_value}${unit}"

  echo $new_resource
}

# Function to update the deployment with new resource requests and limits
update_deployment_resources() {
  local new_cpu_request=$1
  local new_mem_request=$2
  local new_cpu_limit=$3
  local new_mem_limit=$4
  if [[ "$current_mem_request" == "null" ]]; then
    echo "Modifying memory resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/cpu", "value": "'$new_cpu_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/cpu", "value": "'$new_cpu_limit'"}]'
  else
    echo "Modifying memory resources"
    kubectl patch deployment $deployment_name -n $namespace --type='json' -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/cpu", "value": "'$new_cpu_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/requests/memory", "value": "'$current_mem_request'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/cpu", "value": "'$new_cpu_limit'"}, {"op": "replace", "path": "/spec/template/spec/containers/0/resources/limits/memory", "value": "'$current_mem_limit'"}]'
  fi
}

# Get the available cluster resources
read available_cpu available_mem <<< $(get_cluster_resources)

# Convert available memory from Mi to Gi for easier comparison
available_mem_gi=$(echo "scale=2; $available_mem / 1024" | bc)

# Get current deployment resource requests and limits
current_resources=$(kubectl get deployment $deployment_name -n $namespace -o jsonpath='{.spec.template.spec.containers[0].resources}')
current_cpu_request=$(echo $current_resources | jq -r '.requests.cpu')
current_mem_request=$(echo $current_resources | jq -r '.requests.memory')
current_cpu_limit=$(echo $current_resources | jq -r '.limits.cpu')
current_mem_limit=$(echo $current_resources | jq -r '.limits.memory')


echo "Available CPU: $available_cpu "
echo "Current CPU Request: $current_cpu_request"
echo "Current CPU Limit: $current_cpu_limit"

# New resource requests and limits based on available resources (example: setting requests to 10% of available resources and limits to 20%)
new_cpu_request=$(increase_resource $current_cpu_request 20)
new_cpu_limit=$(increase_resource $current_cpu_limit 20)



# Update the deployment with new resource requests and limits
update_deployment_resources $new_cpu_request $current_mem_request $new_cpu_request $current_mem_limit

echo "Deployment resources updated to:"
echo "New CPU Request: $new_cpu_request"
echo "New CPU Limit: $new_cpu_limit"
