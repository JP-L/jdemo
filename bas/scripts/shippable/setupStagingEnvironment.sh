#!/bin/bash -ex
#
# Configure the environment(s)

# Script variables
DEBUG=0
LOCAL=0
SRCPATH=""  				#IN/scripts-from-srcRepo/gitRepo/src/main/
MODULE=""					#terraform
TF_PLAN="tfplan"
WORKINGDIR="" 				#$SCRIPTSFROMSRCREPO_STATE/tf-temp
AWS_ACCESS_KEY_ID="" 		#$AWSCLICONFIG_INTEGRATION_AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY=""	#$AWSCLICONFIG_INTEGRATION_AWS_SECRET_ACCESS_KEY
AWS_DEFAULT_REGION=""		#$AWSCLICONFIG_POINTER_REGION

# Some helper functions
function log_debug () {
    if [[ "$DEBUG" -eq 1 ]]; then
        echo "DEBUG $@"
    fi
}
function log_info () {
    echo "INFO $@"
}

# Reading the commandline arguments
POSITIONAL=()
while [[ "$#" -gt 0 ]]
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
    SRCPATH="$2"
    shift # past argument
    shift # past value
    ;;
    -w|--workingdir)
    WORKINGDIR="$2"
    shift # past argument
    shift # past value
    ;;
    -m|--module)
    MODULE="$2"
    shift # past argument
    shift # past value
    ;;
    -ak|--accessKey)
    AWS_ACCESS_KEY_ID="$2"
    shift # past argument
    shift # past value
    ;;
    -sk|--secretKey)
    AWS_SECRET_ACCESS_KEY="$2"
    shift # past argument
    shift # past value
    ;;
    -r|--region)
    AWS_DEFAULT_REGION="$2"
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


log_info "==== Prepare container environment ===="
if [ "$LOCAL" -eq 0 ]; then
	if [ ! -z "$AWS_ACCESS_KEY_ID" ]; then export AWS_ACCESS_KEY_ID="$AWS_ACCESS_KEY_ID"; fi
	if [ ! -z "$AWS_SECRET_ACCESS_KEY" ]; then export AWS_SECRET_ACCESS_KEY="$AWS_SECRET_ACCESS_KEY"; fi
	if [ ! -z "$AWS_DEFAULT_REGION" ]; then export AWS_DEFAULT_REGION="$AWS_DEFAULT_REGION"; fi
fi

if [ ! -d "$WORKINGDIR" ]; then
	log_debug "==== Create working dir $WORKINGDIR ===="
	mkdir -p "$WORKINGDIR"
else
	# clean the working dir
	rm -R "$WORKINGDIR/"
fi
if [ -d "$SRCPATH/$MODULE" ]; then
	log_debug "==== Copy $SRCPATH/$MODULE/. into $WORKINGDIR ===="
	cp -R "$SRCPATH/$MODULE/." "$WORKINGDIR"
fi
if [ -d "$SRCPATH/resources/$MODULE" ]; then
	log_debug "==== Copy $SRCPATH/resources/$MODULE/. into $WORKINGDIR ===="
	cp -R "$SRCPATH/resources/$MODULE/." "$WORKINGDIR"
fi

log_debug "==== DEBUG List all environment variables ====" && printenv               
 
log_info "==== Configure testing environment for testing Alpha, Beta and RC release ===="  
cd "$WORKINGDIR"
log_info "==== init ===="
terraform init -input=false "$WORKINGDIR"
log_info "==== plan ===="
terraform plan -out "$WORKINGDIR/$TF_PLAN" -input=false "$WORKINGDIR"

if [[ "$DEBUG" -eq 1 ]]; then
	log_debug "==== show ===="
	terraform show -module-depth=-1 "$WORKINGDIR/$TF_PLAN"
fi

log_info "==== apply ===="
#terraform apply -input=false "$WORKINGDIR/$TF_PLAN"

log_info "==== Store the LB ARN ===="
if [[ "$DEBUG" -eq 1 ]]; then
	log_debug "==== output ===="
	terraform output | grep "$AWS_DEFAULT_REGION" | cut -d'=' -f 2- | sed -e 's/^[ \t]*//'
fi
ARN=$(terraform output | grep "$AWS_DEFAULT_REGION" | cut -d'=' -f 2- | sed -e 's/^[ \t]*//')
log_debug "LB ARN $ARN"
if [ "$LOCAL" -eq 0 ]; then
	shipctl put_resource_state EUC1-ELB-QA-cluster sourceName "$ARN"
fi

exit 0;