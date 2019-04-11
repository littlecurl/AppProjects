#!/bin/bash

ADD_OPENS_OPTION="--add-opens java.base/java.lang=ALL-UNNAMED"
java ${ADD_OPENS_OPTION} -version > /dev/null 2>&1
if [ $? -eq 0 ]; then
  # running on jdk9+
  JAVA_OPTIONS="${ADD_OPENS_OPTION}"
else
  # running on jdk8
  JAVA_OPTIONS=""
fi

JAVA_OPTIONS="${JAVA_OPTIONS} -Xmx1024m -Xms512m -XX:MaxMetaspaceSize=256m"
JAR_FILE="target/luke-swing-with-deps.jar"
if [[ ! -d `echo ${LUKE_PATH}` ]]; then
  LUKE_PATH=$(cd $(dirname $0) && pwd)
  echo "Unable to find the LUKE_PATH environnement variable."
  echo "Set LUKE_PATH to ${LUKE_PATH}"
fi

cd ${LUKE_PATH}
nohup java ${JAVA_OPTIONS} -jar ${JAR_FILE} > ${HOME}/.luke.d/luke_out.log 2>&1 &