#!/bin/bash

# Adds given jReality jar to the local maven repository.
# This script will be called from the maven validation
# task.

file="$1"
filename=$(basename "$file" )
name=$(basename "$filename" .jar )
version="1.0"

# http://stackoverflow.com/questions/4774054/reliable-way-for-a-bash-script-to-get-the-full-path-to-itself
scriptpath="$( cd "$(dirname "$0")" ; pwd -P )"
projectroot=$(readlink -f "$scriptpath/../../../")
localMaven="$projectroot/lib/maven-repo"

echo "current script: $scriptpath";
echo "local maven repo: $localMaven";
echo "add jar $file as jreality.$name with version $version to maven"

mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file \
    -Dfile="$file" \
    -DgroupId=jreality \
    -DartifactId="$name" \
    -Dversion="$version" \
    -Dpackaging=jar \
    -DlocalRepositoryPath="$localMaven" \
    -DcreateChecksum=true

