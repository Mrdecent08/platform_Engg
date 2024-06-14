
#!/bin/bash

# Variables
alertName="$1"
podName="$2"
namespace="${3:-default}"
replica_set=$(kubectl get pod $podName -n $namespace -o jsonpath='{.metadata.ownerReferences[0].name}')
deployment_name=$( kubectl get replicaset $replica_set -n $namespace -o jsonpath='{.metadata.ownerReferences[0].name}')

phase=$(k get pod $podName -n $namespace -o jsonpath='{.status.phase}')
current_replicas=$(k get deploy $deployment_name -n $namespace -o jsonpath='{.status.replicas}')
if [ -z "$current_replicas" ]; then
  current_replicas=1
fi

if [[ "$phase" == "Running" ]]; then
  echo "Increase Replicas to reduce stress on a pod"
  new_replicas=$((current_replicas + 1))
  kubectl patch deployment $deployment_name -n $namespace -p "{\"spec\": {\"replicas\": $new_replicas}}"
fi
