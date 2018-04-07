#!/bin/bash -e
#
DEBUG=0
DEBUG_OPTION="--info"
POST_CI=0
SUCCESS=0
FAILURE=0
RESOURCE=""

POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -d|--debug)
    DEBUG=1
    DEBUG_OPTION="--debug"
    shift # past argument
    #shift # past value
    ;;
    -p|--post)
    POST_CI=1
    shift # past argument
    #shift # past value
    ;;
    -s|--success)
    SUCCESS=1
    shift # past argument
    #shift # past value
    ;;
    -f|--failure)
    FAILURE=1
    shift # past argument
    #shift # past value
    -r|--resource)
    FAILURE=1
    shift # past argument
    shift # past value
    ;;
    --default)
    DEFAULT=YES
    shift # past argument
    ;;
    *)    # unknown option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

if [[ "$DEBUG" -eq 1 ]]; then
	set -o xtrace
fi

if [ "$POST_CI" -eq 1 ]; then
	echo "==== Post CI tasks ==== ";
    if [ "$BRANCH" == "releases" ]; then
    	echo "==== Set $IMAGE_REPOSITORY with $IMAGE_VERSION for $BUILDNUMBER ==== ";
    	shipctl put_resource_state "$RESOURCE" sourceName "$IMAGE_REPOSITORY";
    	shipctl put_resource_state "$RESOURCE" versionName "$IMAGE_VERSION";
    	shipctl put_resource_state "$RESOURCE" buildNumber "$BUILD_NUMBER";
    elif [ "$BRANCH" == "master" ]; then
    	shipctl put_resource_state "$RESOURCE" sourceName "$IMAGE_REPOSITORY";
    	shipctl put_resource_state "$RESOURCE" versionName "$IMAGE_VERSION";
    	shipctl put_resource_state "$RESOURCE" buildNumber "$BUILD_NUMBER";
    else
    	echo "==== No Post CI tasks required ====";
    fi
   
elif [ "$SUCCESS" -eq 1 ]; then
	if [ "$BRANCH" == "development" ]; then
		echo "==== Release the code for Acceptance and Smoke testing ====";
		gradle releaseDevelopment "$DEBUG_OPTION";
	elif [ "$BRANCH" == "releases" ]; then
		echo "==== Tag as alpha release ====";
		gradle releaseAlpha "$DEBUG_OPTION";
	elif [ "$BRANCH" == "master" ]; then
		echo "==== Publish the next Release ====";
		gradle publishRelease "$DEBUG_OPTION";
	else
		echo "==== Unknown branch ====";
	fi
elif [ "$FAILURE" -eq 1 ]; then
	echo "==== Build failed ===="
fi

exit 0;