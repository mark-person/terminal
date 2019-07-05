#!/bin/sh
JDK_PATH="/home/ppx/jdk/jdk-11.0.1/"
APP_PATH="/home/ppx/terminal/"
LOG_PATH="/home/ppx/terminal/log/"
JAVA_OPTS="-server -Xmx1024m -Xms256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_PATH}app1_OutOfMemory_`date +"%Y%m%d"`.dump"
JAVA_OPTS="${JAVA_OPTS} --add-opens java.base/java.lang=ALL-UNNAMED -XX:+IgnoreUnrecognizedVMOptions --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"

read oldPid < ${APP_PATH}app1_pid
kill -9 ${oldPid}
LOG_FILE=${LOG_PATH}app1_log_`date +"%Y%m%d"`.txt
nohup ${JDK_PATH}bin/java -Dspring.profiles.active=app1 ${JAVA_OPTS} -jar ${APP_PATH}terminal-server-0.0.1-SNAPSHOT.jar >> ${LOG_FILE} 2>&1 &
pid=$!
echo ${pid} > ${APP_PATH}app1_pid
ps ${pid}
echo 'Start Begin! >>>>>>>>>>>>'
tail -f ${LOG_FILE}



