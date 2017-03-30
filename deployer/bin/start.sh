#/bin/sh

############ define some various #####################
MAINCLASS=launcher.
REAL_LOG_PATH=/data/logs/test
#JAVA=/usr/jdk1.8.0_65/bin/java
######################################################


################## check jdk version ####################
java_ver_output=`"${JAVA:-java}" -version 2>&1`
jvmver=`echo "$java_ver_output" | grep '[openjdk|java] version' | awk -F'"' 'NR==1 {print $2}' | cut -d\- -f1`
JVM_VERSION=${jvmver%_*}

if [ "$JVM_VERSION" \< "1.8" ] ; then
    echo "require Java 8 or later."
    exit 1;
fi


#################### current dir ######################
HOME=$(dirname $(cd `dirname $0`; pwd))
cd $HOME
PROCESS_PATH=$HOME

##################### set classpath ##################
CLASSPATH="${HOME}/conf/:$CLASSPATH"
CLASSPATH="${HOME}/lib/*:$CLASSPATH"

#################### set jvm options #################
JVM_OPTS="-Xloggc:${HOME}/logs/gc.log"
JVM_OPTS_FILE=${HOME}/bin/jvm.options
for opt in `grep "^-" $JVM_OPTS_FILE`
do
  JVM_OPTS="$JVM_OPTS $opt"
done


########### make log dir #########
if [ ! -e "${REAL_LOG_PATH}" ]; then
        mkdir -p ${REAL_LOG_PATH}
fi
rm -rf ${HOME}/logs
ln -s ${REAL_LOG_PATH} ${HOME}/logs


######## function ########
get_pid() {
        JAVA_PID=`ps -C java -f |grep "$1"|grep -v grep|awk '{print $2}'`
        echo $JAVA_PID;
}


############  stop server if necessary ##########
PID=`get_pid "${PROCESS_PATH}"`
if [ -n "$PID" ]; then
        sh ${HOME}/bin/stop.sh
fi


################## start server ###################
nohup ${JAVA:-java} ${JVM_OPTS} -cp ${CLASSPATH} ${MAINCLASS} ${PROCESS_PATH} > ${HOME}/logs/stdout.log 2>&1 &

sleep 1
PID=`get_pid "${PROCESS_PATH}"`
if [ -z "$PID" ]; then
        echo "start failed!"
        exit 1
else
        echo "start success pid:"$PID
fi

exit 0
