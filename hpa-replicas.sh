#!/bin/bash

# Configuration
namespace=$3
hpa_name=$2
scale_increment=2
check_interval=60

# Function to get current and max replicas
get_hpa_replicas() {
  current_replicas=$(kubectl get hpa $hpa_name -n $namespace -o jsonpath='{.status.currentReplicas}')
  max_replicas=$(kubectl get hpa $hpa_name -n $namespace -o jsonpath='{.spec.maxReplicas}')
}

# Function to update max replicas
update_max_replicas() {
  new_max_replicas=$((max_replicas + scale_increment))
  kubectl patch hpa $hpa_name -n $namespace -p "{\"spec\": {\"maxReplicas\": $new_max_replicas}}"
  echo "Updated HPA max replicas to $new_max_replicas"
}

# Main script

get_hpa_replicas
echo "Current replicas: $current_replicas, Max replicas: $max_replicas"
  
if [ "$current_replicas" -eq "$max_replicas" ]; then
  echo "HPA $hpa_name is at maximum replicas. Increasing the max replicas..."
  update_max_replicas
fi

