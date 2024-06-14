
#!/bin/bash

# Variables
alertName="$1"
podName="$2"
namespace="${3:-default}"

JIRA_URL="https://abhishek-v.atlassian.net/rest/api/2/issue"
AUTH_HEADER="Basic YWJoaXNoZWsudmVsaWNoYWxhQHRjcy5jb206QVRBVFQzeEZmR0YwLVBRamJoaXUtUWdMTUptcUZ0dUhlc2NrZWN6eHUycEo2Y0FsaTk3X3gzWFlWSmQzeHNzNjNtSUFWMVBzOE1aM0lQbi1TU1dwaTMwaDlLbzd1QTNOMmszSEhnMFFIUlltT1dXMnJlOVNONUQ3M0xaV3lxb2dzbGdzUUMwcWQzSTBFTVh4YTRwTXRiZTh1ajB5M0cyc250M0hUZllJUDgwYUVFTWxrU0FBenFjPTcyNTYxNzFE"


# Function to raise a ticket
raise_ticket() {
  local summary="$1"
  local description="$2"
  local JSON_PAYLOAD=$(cat <<EOF
{
  "fields": {
    "project": {
      "key": "SH"
    },
    "summary": "$summary",
    "description": "$description",
    "issuetype": {
      "name": "Task"
    }
  }
}
EOF
)

  curl -X POST \
  "$JIRA_URL" \
  -H "Content-Type: application/json" \
  -H "Authorization: $AUTH_HEADER" \
  -d "$JSON_PAYLOAD"
}
# Function to find image details from pod
get_image_details() {
  kubectl get pods "$podName" -n "$namespace" -o jsonpath='{.spec.containers[0].image}'
}

# Function to extract image user, name, and tag
extract_image_parts() {
  local image="$1"
  image_user=$(echo "$image" | sed -E 's/(.*\/)?([^:]+):([^:]+)$/\1/')
  image_name=$(echo "$image" | sed -E 's/(.*\/)?([^:]+):([^:]+)$/\2/')
  image_tag=$(echo "$image" | sed -E 's/(.*\/)?([^:]+):([^:]+)$/\3/')
}

# Function to check if image exists locally
check_image_exists() {
  docker images | grep "${image_user}${image_name}" | awk '{print $1}' | head -1
}

# Function to push image to registry
push_image() {
  docker logout
  docker login 10.63.12.180:32003 -uadmin -padmin
  existing_tag=$(docker images | grep "${image_user}${image_name}" | awk '{print $2}' | head -1)
  docker tag "${image_user}${image_name}:${existing_tag}" "${image_user}${image_name}:${image_tag}"
  docker push "${image_user}${image_name}:${image_tag}"
}

# Function to restart the pod
restart_pod() {
  secret = $(kubectl get pods image-mismatch-65ff9d8d89-6j9sz -n remediation -o jsonpath='{.spec.imagePullSecrets[0].name}')
  kubectl delete secret "$secret" -n "$namespace"
  kubectl create secret docker-registry "$secret" --docker-server=http://10.63.12.180:32003 --docker-username=admin --docker-password=admin -n "$namespace"
  kubectl delete pod "$podName" -n "$namespace"
}

# Main script execution
image_details=$(get_image_details)
echo "Current Docker images:"
docker images
if [[ -z "$image_details" ]]; then
  echo "No image details found for pod $podName in namespace $namespace"
  exit 1
fi

echo "Image details: $image_details"

extract_image_parts "$image_details"

echo "Extracted image user: $image_user"
echo "Extracted image name: $image_name"
echo "Extracted image tag: $image_tag"

if [[ -z "$image_user" || -z "$image_name" || -z "$image_tag" ]]; then
  echo "Failed to extract image parts from $image_details"
  exit 1
fi

image_status=$(check_image_exists)

if [[ -n "$image_status" ]]; then
  push_image
  echo "Image pushed successfully: ${image_user}${image_name}:${image_tag}"
else
  echo "Image ${image_user}${image_name} does not exist locally"
  msg="Image $image not found in the registry"
  raise_ticket "$alertName" "$msg"
  #############################################################################
  echo "raise a ticket as images doesn't exist"
  #############################################################################
  exit 1
fi

restart_pod

echo "Pod $podName in namespace $namespace has been restarted."