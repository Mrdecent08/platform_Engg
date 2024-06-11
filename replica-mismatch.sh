#!/bin/bash

# Variables
alertName="${1}"
podName="${2}"
namespace="${3:-default}"

# Get pods data
pod_info=$(kubectl get pods -n "$namespace" | grep "$podName" | awk '{ print $1 }' | head -1)

# Check if pod_info is empty
if [[ -z "$pod_info" ]]; then
  echo "No pod found matching the name $podName in namespace $namespace"
  exit 1
fi

# Find labels attached to pod
labels=$(kubectl get pods "$pod_info" -n "$namespace" -o jsonpath='{.spec.nodeSelector}' | jq -r 'to_entries[] | "\(.key)=\(.value)"')

# Check if labels are empty
if [[ -z "$labels" ]]; then
  echo "No labels found for pod $pod_info"
  exit 1
fi

# Get node data
node_info=$(kubectl get nodes --no-headers | awk '{ print $1 }' | head -1)

# Check if node_info is empty
if [[ -z "$node_info" ]]; then
  echo "No nodes found"
  exit 1
fi

# Check if any node has the label
node_names=$(kubectl get nodes -l "$labels" -o jsonpath='{.items[*].metadata.name}')

# Add label to node if no node has the label
if [[ -z "$node_names" ]]; then
  kubectl label nodes "$node_info" "$labels"
fi

# Display pod labels
echo "Labels attached to pod $pod_info: $labels"
