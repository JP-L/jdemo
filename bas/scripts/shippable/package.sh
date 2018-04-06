#!/bin/bash -e
#
DEBUG=0
DEBUG_OPTION=
LOCAL=0
GRADLE_PROPS=""		#$SHIPPABLE_REPO_DIR/bas/resources/gradle/gradle.properties

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
    DEBUG=1
    DEBUG_OPTION="--debug"
    shift # past argument
    #shift # past value
    ;;
    -l|--local)
    LOCAL=1
    shift # past argument
    #shift # past value
    ;;
    -src|--source)
    GRADLE_PROPS="$2"
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

log_info "==== Building the Java application ===="

gradle assemble "$DEBUG_OPTION";
if [ "$BRANCH" != "master" ]; then
	log_info "==== Tests and Quality control for the Java application ====";  
    gradle inspectQuality "$DEBUG_OPTION";
    gradle runFunctionalAndIntegrationTests "$DEBUG_OPTION";
elif [ "$BRANCH" == "master" ]; then
	log_info "==== Tests for the Java application ====";  
    gradle test "$DEBUG_OPTION";
    gradle runFunctionalAndIntegrationTests "$DEBUG_OPTION";
else
	log_info "==== Unknown branch ====";
fi

exit 0;