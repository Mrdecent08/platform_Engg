
#!/bin/bash

alertName="$1"
podName="$2"
namespace="${3:-default}"

# Main script

# Get the finalizers associated with the pod
finalizers=$(kubectl get pod $podName -n $namespace -o jsonpath='{.metadata.finalizers}')

# Check if there are any finalizers
if [ -z "$finalizers" ]; then
  echo "No finalizers are associated with the pod."
else
  echo "The following finalizers are associated with the pod:"
  echo $finalizers
  kubectl -n $namespace patch pod $podName -p '{"metadata":{"finalizers": []}}'
fi

kubectl delete pod $podName -n $namespace --force