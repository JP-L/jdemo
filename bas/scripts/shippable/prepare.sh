#!/bin/bash -e
#
echo "==== Prepare Gradle environment ===="

# Create folder structure and prepare the property file
mkdir -p $HOME/.gradle
cp $SHIPPABLE_REPO_DIR/bas/resources/gradle/gradle.properties $HOME/.gradle/

sed -i -r "s|BURL|$BINREPO_URL|g" $HOME/.gradle/gradle.properties
sed -i -r "s|REG_URL|$BINREPO_REGURL|g" $HOME/.gradle/gradle.properties
sed -i -r "s|USR|$BINREPO_USR|g" $HOME/.gradle/gradle.properties
sed -i -r "s|PWD|$BINREPO_PWD|g" $HOME/.gradle/gradle.properties
sed -i -r "s|EMAIL|$BINREPO_EMAIL|g" $HOME/.gradle/gradle.properties

sed -i -r "s|SONAR_URL|$SONAR_URL|g" $HOME/.gradle/gradle.properties
sed -i -r "s|ORG|$ORGANIZATION|g" $HOME/.gradle/gradle.properties
sed -i -r "s|SONAR_KEY|$SONAR_KEY|g" $HOME/.gradle/gradle.properties
      
sed -i -r "s|SRCREPO_KEY|$SRCREPO_USR|g" $HOME/.gradle/gradle.properties
      
sed -i -r "s|MY_ACCESS_ID|$AMAZONKEYS_ACCESSKEY|g" $HOME/.gradle/gradle.properties 
sed -i -r "s|MY_SECRET|$AMAZONKEYS_SECRETKEY|g" $HOME/.gradle/gradle.properties

exit 0;