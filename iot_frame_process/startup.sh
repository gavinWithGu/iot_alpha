#!/bin/bash
echo "Starting timer..."
if [ -f /opt/jar/timer/timerPid ]
        then
                kill -9 `cat /opt/jar/timer/timerPid` 2>/opt/jar/timer/logs/timer.log
                rm -rf /opt/jar/timer/timerPid
fi
nohup java -jar -Xms2g -Xmx2g -Xmn1g -Xss512k -XX:PermSize=512m -XX:MaxPermSize=512m  -XX:ParallelGCThreads=8 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime -Xloggc:gc.log  /opt/jar/timer/timer.jar >/opt/jar/timer/logs/timer.log 2>&1 &
echo $! > /opt/jar/timer/timerPid
echo "Start timer finished"