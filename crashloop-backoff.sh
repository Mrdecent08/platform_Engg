#!/bin/bash

# Variables
alertName="${1}"
podName="${2}"
namespace="${3:-default}"

#Restart the kubernetes Pod
kubectl delete pod $podName -n $namespace