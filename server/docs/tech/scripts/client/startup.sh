#!/bin/sh
JDK_PATH="/home/ppx/jdk/jdk-11.0.1/"
APP_PATH="/home/ppx/client/"
LOG_PATH="/home/ppx/client/log/"
JAVA_OPTS="-server -Xmx1024m -Xms256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_PATH}OutOfMemory_`date +"%Y%m%d"`.dump"
# JAVA_OPTS="${JAVA_OPTS} --add-opens java.base/java.lang=ALL-UNNAMED -XX:+IgnoreUnrecognizedVMOptions --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"
JAVA_OPTS = ""
LOG_FILE=${LOG_PATH}log_`date +"%Y%m%d"`.txt

read oldPid < ${APP_PATH}pid
kill -9 ${oldPid}
nohup java -Dspring.profiles.active=dev ${JAVA_OPTS} -jar ${APP_PATH}terminal-client-0.0.1-SNAPSHOT.jar >> ${LOG_FILE} 2>&1 &
pid=$!
echo ${pid} > ${APP_PATH}pid
ps ${pid}
echo 'Start Begin! >>>>>>>>>>>>'
tail -f ${LOG_FILE}



