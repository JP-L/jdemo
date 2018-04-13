#!/bin/bash -e
#
DEBUG=0
LOCAL=0
GRADLE_PROPS=""		#$SHIPPABLE_REPO_DIR/bas/resources/gradle/gradle.properties
OPTIONS=()

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

# Handle Alpha releases
log_info "==== run tests for testing Alpha release ===="
gradle runAcceptanceAndSmokeTests "${OPTIONS[@]}" -Pstage=ALPHA
log_info "==== set Beta release on success ===="
gradle releaseBeta "${OPTIONS[@]}"
# Handle Beta releases
log_info "==== run tests for testing Beta release ===="
gradle runAcceptanceAndSmokeTests "${OPTIONS[@]}" -Pstage=BETA
log_info "==== set RC release on success ===="
gradle releaseCandidate "${OPTIONS[@]}"
# Handle RC releases
log_info "==== run RC tests if any ===="
gradle runAcceptanceAndSmokeTests "${OPTIONS[@]}" -Pstage=RC
# Clean up and release the new version
log_info "==== clean up deployed image and test env ===="
#Need to find out how
log_info "==== Release the next version ===="
gradle releaseVersion "${OPTIONS[@]}"

exit 0;