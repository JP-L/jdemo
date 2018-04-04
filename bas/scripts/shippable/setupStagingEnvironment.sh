#!/bin/bash -e
#
# Configure the environment(s)
ARG="$1"

function log () {
    if [[ $ARG -eq "--debug" ]]; then
        echo "$@"
    fi
}

echo "==== Prepare container environment ===="

BASE_SRCDIR=IN/scripts-from-srcRepo/gitRepo/src/main

TF_DIR=tf-temp
TF_PLAN=tf_plan
TERRAFORM_WORKINGDIR=$SCRIPTSFROMSRCREPO_STATE/$TF_DIR

export AWS_ACCESS_KEY_ID=$AWSCLICONFIG_INTEGRATION_AWS_ACCESS_KEY_ID
export AWS_SECRET_ACCESS_KEY=$AWSCLICONFIG_INTEGRATION_AWS_SECRET_ACCESS_KEY
export AWS_DEFAULT_REGION=$AWSCLICONFIG_POINTER_REGION


mkdir -p $TERRAFORM_WORKINGDIR
if [ -d "$BASE_SRCDIR/terraform" ]; then
	cp -r "$BASE_SRCDIR/terraform" $TERRAFORM_WORKINGDIR
fi

if [ -d "$BASE_SRCDIR/resources/terraform" ]; then
	cp -r "$BASE_SRCDIR/resources/terraform" $TERRAFORM_WORKINGDIR
fi

log "==== DEBUG List all environment variables ====" && printenv               
              - 
echo "==== Configure testing environment for testing Alpha, Beta and RC release ===="  
cd $TERRAFORM_WORKINGDIR
terraform init -input=false $TERRAFORM_WORKINGDIR
terraform plan -out $TERRAFORM_WORKINGDIR/$TF_PLAN -input=false $TERRAFORM_WORKINGDIR

if [[ "$ARG" -eq "--debug" ]]; then
	terraform show -module-depth=-1 $TERRAFORM_WORKINGDIR/$TF_PLAN
else
	terraform apply -input=false $TERRAFORM_WORKINGDIR/$TF_PLAN
fi

echo "==== Store the LB ARN ===="
terraform output | grep $REGION | cut -d'=' -f 2- | sed -e 's/^[ \t]*//'
ARN=$(terraform output | grep $REGION | cut -d'=' -f 2- | sed -e 's/^[ \t]*//') &&

log "$ARN"

shipctl put_resource_state EUC1-ELB-QA-cluster sourceName $ARN

exit 0;