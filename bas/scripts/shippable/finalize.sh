#!/bin/bash -e
#
POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -p|--post)
    POST_CI="$2"
    shift # past argument
    shift # past value
    ;;
    -s|--success)
    SUCCESS="$2"
    shift # past argument
    shift # past value
    ;;
    -f|--failure)
    FAILURE="$2"
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


echo "==== Finish CI ===="

if [ -z "$POST_CI" ]; then
	echo "==== Post CI tasks ==== ";
    if [ "$BRANCH" == "releases" ]; then
    	echo "==== Set $IMAGE_REPOSITORY with $IMAGE_VERSION for $BUILDNUMBER ==== ";
    	shipctl put_resource_state jdemoImgQA sourceName $IMAGE_REPOSITORY;
    	shipctl put_resource_state jdemoImgQA versionName $IMAGE_VERSION;
    	shipctl put_resource_state jdemoImgQA buildNumber $BUILD_NUMBER;
    elif [ "$BRANCH" == "master" ]; then
    	shipctl put_resource_state jdemoImg sourceName $IMAGE_REPOSITORY;
    	shipctl put_resource_state jdemoImg versionName $IMAGE_VERSION;
    	shipctl put_resource_state jdemoImg buildNumber $BUILD_NUMBER;
    else
    	echo "==== No Post CI tasks required ====";
    fi
   
elif [ -z "$SUCCESS" ]; then
	if [ "$BRANCH" == "development" ]; then
		echo "==== Release the code for Acceptance and Smoke testing ====";
		gradle releaseDevelopment;
	elif [ "$BRANCH" == "releases" ]; then
		echo "==== Tag as alpha release ====";
		gradle releaseAlpha;
	elif [ "$BRANCH" == "master" ]; then
		echo "==== Publish the next Release ====";
		gradle publishRelease;
	else
		echo "==== Unknown branch ====";
	fi
elif [ -z "$FAILURE" ]; then
	echo "==== Build failed ===="
fi

exit 0;