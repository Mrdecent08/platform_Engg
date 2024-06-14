#!/bin/bash

alertName="$1"
podName="$2"
namespace="${3:-default}"
NAMESPACE="remediation"  # Namespace of the deployment
DEPLOYMENT="replicas"    # Name of the deployment

# Function to detect pods running on the same node
detect_single_node_replicas() {
  # Get the nodes where each pod of the deployment is running
  nodes=$(kubectl get pods -n "$NAMESPACE" -l "app=$DEPLOYMENT" -o jsonpath='{.items[*].spec.nodeName}')

  # Get unique nodes
  unique_nodes=$(echo "$nodes" | tr ' ' '\n' | sort -u | wc -l)

  # Get total replicas
  replicas=$(kubectl get deployment "$DEPLOYMENT" -n "$NAMESPACE" -o jsonpath='{.spec.replicas}')

  if [ "$unique_nodes" -lt "$replicas" ]; then
    echo "All replicas of $DEPLOYMENT in namespace $NAMESPACE are running on the same node."
    return 0
  else
    echo "Replicas of $DEPLOYMENT in namespace $NAMESPACE are well-distributed."
    return 1
  fi
}

# Function to apply PodAntiAffinity to a deployment using kubectl patch
apply_pod_anti_affinity() {
  # Get the current number of replicas
  replicas=$(kubectl get deployment "$DEPLOYMENT" -n "$NAMESPACE" -o jsonpath='{.spec.replicas}')

  kubectl patch deployment "$DEPLOYMENT" -n "$NAMESPACE" --type='json' -p='[
    {
      "op": "add",
      "path": "/spec/template/spec/affinity",
      "value": {
        "podAntiAffinity": {
          "requiredDuringSchedulingIgnoredDuringExecution": [
            {
              "labelSelector": {
                "matchExpressions": [
                  {
                    "key": "app",
                    "operator": "In",
                    "values": ["'"$DEPLOYMENT"'"]
                  }
                ]
              },
              "topologyKey": "kubernetes.io/hostname"
            }
          ]
        }
      }
    },
    {
      "op": "replace",
      "path": "/spec/replicas",
      "value": '"$replicas"'
    }
  ]'
  echo "Applied PodAntiAffinity to $DEPLOYMENT in namespace $NAMESPACE with $replicas replicas."
}

# Main script
if detect_single_node_replicas; then
  apply_pod_anti_affinity
else
  echo "No remediation needed."
fi
