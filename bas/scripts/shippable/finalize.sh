#!/bin/bash -e
#
DEBUG=0
LOCAL=0
GRADLE_PROPS=""		#$SHIPPABLE_REPO_DIR/bas/resources/gradle/gradle.properties
OPTIONS=()

POST_CI=0
SUCCESS=0
FAILURE=0
RESOURCE=""

# Some helper functions
function log_debug () {
    if [[ "$DEBUG" -eq 1 ]]; then
        echo "$@"
    fi
}
function log_info () {
    echo "$@"
}

# Reading the commandline arguments
POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -d|--debug)
    set -o xtrace
    OPTIONS+=("--debug")
    shift # past argument
    #shift # past value
    ;;
    -s|--stacktrace)
	OPTIONS+=("--stacktrace")
    shift # past argument
    #shift # past value
    ;;
    -l|--local)
    LOCAL=1
    shift # past argument
    #shift # past value
    ;;
    --postCI)
    POST_CI=1
    shift # past argument
    #shift # past value
    ;;
    --success)
    SUCCESS=1
    shift # past argument
    #shift # past value
    ;;
    --failure)
    FAILURE=1
    shift # past argument
    #shift # past value
    ;;
    -r|--resource)
    RESOURCE="$2"
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

if [ "$POST_CI" -eq 1 ]; then
	log_info "==== Set $IMAGE_REPOSITORY with $IMAGE_VERSION for $BUILDNUMBER on branch $BRANCH  for resource $RESOURCE ==== ";
	if [ "$LOCAL" -eq 0 ]; then
   		shipctl put_resource_state "$RESOURCE" sourceName "$IMAGE_REPOSITORY";
   		shipctl put_resource_state "$RESOURCE" versionName "$IMAGE_VERSION";
   		shipctl put_resource_state "$RESOURCE" buildNumber "$BUILD_NUMBER";
   	else
   		log_info "==== Running local. Skipping shipctl commands ====";
   	fi
 
elif [ "$SUCCESS" -eq 1 ]; then
	if [ "$BRANCH" == "development" ]; then
		log_info "==== Release the code for Acceptance and Smoke testing ====";
		gradle releaseDevelopment "${OPTIONS[@]}";
	elif [ "$BRANCH" == "releases" ]; then
		log_info "==== Tag as alpha release ====";
		gradle releaseAlpha "${OPTIONS[@]}";
	elif [ "$BRANCH" == "master" ]; then
		log_info "==== Publish the next Release ====";
		gradle publishRelease "${OPTIONS[@]}";
	else
		log_info "==== Unknown branch ====";
	fi

elif [ "$FAILURE" -eq 1 ]; then
	log_info "==== Build failed ===="
fi

exit 0;