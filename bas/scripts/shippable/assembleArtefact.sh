#!/bin/bash -e
#
echo "==== Building the Java application ===="

gradle assemble;
if [ "$BRANCH" != "master" ]; then
	echo "==== Tests and Quality control for the Java application ====";  
    gradle inspectQuality;
    gradle runFunctionalAndIntegrationTests;
elif [ "$BRANCH" == "master" ]; then
	echo "==== Tests for the Java application ====";  
    gradle test;
    gradle runFunctionalAndIntegrationTests;
else
	echo "==== Unknown branch ====";
fi

exit 0;