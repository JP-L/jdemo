#!/bin/bash -ex
#
DEBUG=0
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

log_info "==== Prepare Gradle environment ===="

# Create folder structure and prepare the property file
BASE="$HOME"
if [ "$LOCAL" -eq 1 ]; then
	BASE="/tmp"
fi

mkdir -p "$BASE/.gradle"
cp "$GRADLE_PROPS" "$BASE/.gradle/"

log_debug "==== Set props in $BASE/.gradle/gradle.properties ===="
sed -i -r "s|BURL|$BINREPO_URL|g" "$BASE/.gradle/gradle.properties"
sed -i -r "s|REG_URL|$BINREPO_REGURL|g" "$BASE/.gradle/gradle.properties"
sed -i -r "s|USR|$BINREPO_USR|g" "$BASE/.gradle/gradle.properties"
sed -i -r "s|PWD|$BINREPO_PWD|g" "$BASE/.gradle/gradle.properties"
sed -i -r "s|EMAIL|$BINREPO_EMAIL|g" "$BASE/.gradle/gradle.properties"

sed -i -r "s|SONAR_URL|$SONAR_URL|g" "$BASE/.gradle/gradle.properties"
sed -i -r "s|ORG|$ORGANIZATION|g" "$BASE/.gradle/gradle.properties"
sed -i -r "s|SONAR_KEY|$SONAR_KEY|g" "$BASE/.gradle/gradle.properties"
      
sed -i -r "s|SRCREPO_KEY|$SRCREPO_USR|g" "$BASE/.gradle/gradle.properties"
      
sed -i -r "s|MY_ACCESS_ID|$AMAZONKEYS_ACCESSKEY|g" "$BASE/.gradle/gradle.properties" 
sed -i -r "s|MY_SECRET|$AMAZONKEYS_SECRETKEY|g" "$BASE/.gradle/gradle.properties"
log_info "==== Gradle environment prepared ===="	

if [ "$DEBUG" -eq 1 ]; then
	printf "%s" "$(<$BASE/.gradle/gradle.properties)"
fi

exit 0;