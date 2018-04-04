#!/bin/bash -e
#
ARG="&1"

echo "==== Finish CI ===="

if [ $ARG -eq "post_ci" ]; then
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
   
elif [ $ARG -eq "on_success" ]; then
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
elif [ $ARG -eq "on_failure" ]; then
	echo "==== Build failed ===="
fi

exit 0;