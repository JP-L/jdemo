#!/bin/bash -e
#
DEBUG=0
DEBUG_OPTION="--info"
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

log_info "==== run tests for testing Alpha release ===="
gradle runFunctionalAndIntegrationTests "$DEBUG_OPTION" -Pstage=ALPHA;
gradle runAcceptanceAndSmokeTests "$DEBUG_OPTION" -Pstage=ALPHA;
log_info "==== set Beta release on success ===="
gradle releaseBeta "$DEBUG_OPTION"
log_info "==== run tests for testing Beta release ===="
gradle runFunctionalAndIntegrationTests "$DEBUG_OPTION" -Pstage=BETA;
gradle runAcceptanceAndSmokeTests "$DEBUG_OPTION" -Pstage=BETA;
log_info "==== set RC release on success ===="
gradle releaseCandidate "$DEBUG_OPTION"
log_info "==== run RC tests if any ===="
gradle runFunctionalAndIntegrationTests "$DEBUG_OPTION" -Pstage=RC;
gradle runAcceptanceAndSmokeTests "$DEBUG_OPTION" -Pstage=RC;
log_info "==== clean up deployed image and test env ===="
#Need to find out how
log_info "==== Release the next version ===="
gradle releaseVersion "$DEBUG_OPTION"

exit 0;