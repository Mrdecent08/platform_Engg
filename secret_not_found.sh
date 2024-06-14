
#!/bin/bash

# Variables
alertName="$1"
podName="$2"
namespace="${3:-default}"

# Function to restart the pod
restart_pod() {
  secret = $(kubectl get pods image-mismatch-65ff9d8d89-6j9sz -n remediation -o jsonpath='{.spec.imagePullSecrets[0].name}')
  kubectl delete secret "$secret" -n "$namespace"
  kubectl create secret docker-registry "$secret" --docker-server=http://10.63.16.153:32003 --docker-username=admin --docker-password=admin -n "$namespace"
  kubectl delete pod "$podName" -n "$namespace"
}

# Main script execution

restart_pod

echo "Secrets has been added for Pod $podName in $namespace namespace."