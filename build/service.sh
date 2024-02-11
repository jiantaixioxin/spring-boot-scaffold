#!/bin/bash

# Spring-Boot 常规启动脚本，基于HotSpot Java8
# 使用方式：sh xx.sh [start|stop|restart|status|dump] [it/st/sit/prod] [debug]

# 项目名称
# SERVICE_NAME:service_name
SERVICE_NAME="${project.artifactId}"
echo "The $SERVICE_NAME :  "

# jar名称
# JAR_NAME:service_name.jar
JAR_NAME="${project.build.finalName}.jar"
echo "The $JAR_NAME :  "

# JAR_PATH:/opt/project/service_name.jar
JAR_PATH=$(readlink -f ${JAR_NAME})

#服务日志目录
LOG_PATH=/appdata/logs/$SERVICE_NAME

#操作命令 [start|stop|restart|status|dump]
COMMAND=$1

# SpringBoot环境名，只需修改此项 [it/st/sit/prod]
ENV_NAME=$2
ENV="-Dspring.profiles.active=$ENV_NAME"

cd $(dirname $0)
DEPLOY_DIR=$(pwd)

# DEBUG_FLAG:debug
DEBUG_FLAG=$3

# TMP_DIR:/opt/project/tmp/service_name
TMP_DIR="$LOG_PATH/tmp"

# OOM_DIR:/opt/project/oom/service_name
OOM_DIR="$LOG_PATH/oom"

# GC_LOG_DIR:/opt/project/gc/service_name
GC_LOG_DIR="$LOG_PATH/gc"

# DUMP_DIR:/opt/project/dump/service_name
DUMP_DIR="$LOG_PATH/dump"
# DUMP目录前缀
DUMP_DATE=$(date +%Y%m%d%H%M%S)
# DUMP目录(带日期)
DUMP_DATE_DIR=$DUMP_DIR/$DUMP_DATE

PID=0

# 运行模式，如无特殊需求，推荐只配置堆+元空间
BASE_OPTS="-server -Xms128m -Xmx1024m -XX:MetaspaceSize=128m"

# 至于Garbage Collector，虽然Java8已经支持G1了，但是不一定必须用，CMS在默认场景下也是一个优秀的回收器
GC_OPTS="-XX:+UseConcMarkSweepGC"

# GC日志参数
GC_LOG_OPTS="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_DIR/gc-%t.log"

# OOM Dump内存参数
DUMP_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$OOM_DIR"

# JVM DEBUG参数，用于调试，默认不开启

# ClassLoader和Method Compile日志，用于调试
COMPILE_LOADER_OPTS="-XX:+TraceClassLoading -XX:+TraceClassUnloading -XX:-PrintCompilation"

# 远程调试参数
REMOTE_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# DEBUG参数
DEBUG_OPTS="$COMPILE_LOADER_OPTS $REMOTE_DEBUG_OPTS"

# SkyWalking Agent
#SKYWALKING_OPTS="-javaagent:/opt/module/skywalking-apm-es7/agent/skywalking-agent.jar=agent.service_name=$SERVICE_NAME"
SKYWALKING_OPTS=""

# 其他参数
OTHER_OPTS="-Djava.io.tmpdir=$TMP_DIR -Djava.security.egd=file:/dev/./urandom"

# JVM 启动参数
JVM_OPTIONS="$BASE_OPTS $GC_OPTS $GC_LOG_OPTS $DUMP_OPTS $OTHER_OPTS"

#使用说明，用来提示输入参数
usage() {
  echo "Usage: sh [run_script].sh [start|stop|restart|status|dump|info|help] [it/st/sit/prod] [debug]"
  exit 1
}

#检查程序是否在运行
is_exist() {
  PID=$(ps -ef | grep $JAR_PATH | grep -v grep | awk '{print $JAR_NAME}')
  #如果不存在返回1，存在返回0
  if [ -z "${PID}" ]; then
    return 1
  else
    return 0
  fi
}

#启动方法
start() {
  echo "=============================start=============================="
  # 不存在临时文件目录，则创建
  if [[ ! -d "$TMP_DIR" ]]; then
    mkdir -p $TMP_DIR
  fi
    # 不存在OOM日志目录，则创建
  if [[ ! -d "$OOM_DIR" ]]; then
    mkdir -p $OOM_DIR
  fi
  # 不存在gc日志目录，则创建
  if [ ! -d $GC_LOG_DIR ]; then
    mkdir -p $GC_LOG_DIR
  fi

  is_exist
  if [ $? -eq "0" ]; then
    echo "--- Service $SERVICE_NAME is already running, PID is $PID ---"
  else
    if [ "$DEBUG_FLAG" = "debug" ]; then
      JVM_OPTIONS="$JVM_OPTIONS $DEBUG_OPTS"
      echo -e "\033[33m Warning: currently running in debug mode! This mode enables remote debugging, printing, compiling, and other information \033[0m"
    fi
    echo "ENV_NAME: $ENV_NAME"
    echo "JVM_OPTIONS: $JVM_OPTIONS"
    nohup java -jar $JVM_OPTIONS $ENV $SKYWALKING_OPTS $JAR_PATH  >/dev/null 2>&1 &
    PID=$!
    if [[ -n $PID ]]; then
       echo "--- Start $SERVICE_NAME successfully, PID is $PID ---"
    else
       echo "--- Failed to start $SERVICE_NAME!!! ---"
    fi
  fi
  echo "=============================start=============================="
}

#停止方法
stop() {
  echo "=============================stop=============================="
  is_exist
  if [ $? -eq "0" ]; then
    echo "--- Begin kill -15 $SERVICE_NAME, PID is $PID ---"
    kill -15 $PID
    sleep 5
    is_exist
    if [ $? -eq "0" ]; then
      echo "--- Stop $SERVICE_NAME failed by kill -15 $PID, begin to kill -9 $PID ---"
      kill -9 $PID
      sleep 2
      echo "--- Stop $SERVICE_NAME successfully by kill -9 $PID ---"
    else
      echo "--- Stop $SERVICE_NAME successfully by kill -15 $PID ---"
    fi
  else
    echo "--- $SERVICE_NAME is not running!!! ---"
  fi
  echo "=============================stop=============================="
}

#输出运行状态
status() {
  echo "=============================status=============================="
  is_exist
  if [ $? -eq "0" ]; then
    echo "--- $SERVICE_NAME is running, PID is $PID ---"
  else
    echo "--- $SERVICE_NAME is not running!!! ---"
  fi
  echo "=============================status=============================="
}

#导出运行信息
dump() {
  echo "=============================dump=============================="
  is_exist
  if [ $? -eq "0" ]; then
    echo -e "Dumping the $SERVICE_NAME ...\c"
    do_dump
  else
    echo "--- $SERVICE_NAME is not running!!! ---"
  fi
  echo "=============================dump=============================="
}

#重启
restart() {
  echo "=============================restart=============================="
  stop
  sleep 2
  start
  echo "=============================restart=============================="
}

#打印当前服务的环境变量和服务的信息
info() {
  echo "=============================info=============================="
  echo "JAR_PATH: $JAR_PATH"
  echo "APP_NAME: $SERVICE_NAME"
  echo "JDK_HOME: $JAVA_HOME"
  echo "ENV_NAME: $ENV_NAME"
  echo "TMP_PATH: $TMP_DIR"
  echo "GC_PATH: $GC_LOG_DIR"
  echo "JVM_OPTS: $JVM_OPTIONS"
  echo "=============================info=============================="
}

#帮助信息
help() {
  echo "start: 启动服务"
  echo "stop: 关闭服务"
  echo "restart: 重启服务"
  echo "status: 显示服务进程ID"
  echo "dump: 导出服务运行信息"
  echo "info: 显示环境变量和服务的信息"
  echo "help: 显示帮助"
  echo "debug: 开启JVM调试模式"
  echo "命令示例：sh service.sh [start|stop|restart|status|dump|info|help] jar_name.jar [debug]"
}

do_dump() {
  # 创建目录
  if [ ! -d $DUMP_DATE_DIR ]; then
    mkdir -p $DUMP_DATE_DIR
  fi

  jstack $PID >$DUMP_DATE_DIR/jstack-$PID.dump 2>&1
  echo -e ".\c"
  jinfo $PID >$DUMP_DATE_DIR/jinfo-$PID.dump 2>&1
  echo -e ".\c"
  jstat -gcutil $PID >$DUMP_DATE_DIR/jstat-gcutil-$PID.dump 2>&1
  echo -e ".\c"
  jstat -gccapacity $PID >$DUMP_DATE_DIR/jstat-gccapacity-$PID.dump 2>&1
  echo -e ".\c"
  jmap $PID >$DUMP_DATE_DIR/jmap-$PID.dump 2>&1
  echo -e ".\c"
  jmap -heap $PID >$DUMP_DATE_DIR/jmap-heap-$PID.dump 2>&1
  echo -e ".\c"
  jmap -histo $PID >$DUMP_DATE_DIR/jmap-histo-$PID.dump 2>&1
  echo -e ".\c"
  jmap -dump:format=b,file=$DUMP_DATE_DIR/jmap-dump-$PID.bin $PID
  echo -e ".\c"
  if [ -r /usr/sbin/lsof ]; then
    /usr/sbin/lsof -p $PID >$DUMP_DATE_DIR/lsof-$PID.dump
    echo -e ".\c"
  fi
  if [ -r /bin/netstat ]; then
    /bin/netstat -an >$DUMP_DATE_DIR/netstat.dump 2>&1
    echo -e ".\c"
  fi
  if [ -r /usr/bin/iostat ]; then
    /usr/bin/iostat >$DUMP_DATE_DIR/iostat.dump 2>&1
    echo -e ".\c"
  fi
  if [ -r /usr/bin/mpstat ]; then
    /usr/bin/mpstat >$DUMP_DATE_DIR/mpstat.dump 2>&1
    echo -e ".\c"
  fi
  if [ -r /usr/bin/vmstat ]; then
    /usr/bin/vmstat >$DUMP_DATE_DIR/vmstat.dump 2>&1
    echo -e ".\c"
  fi
  if [ -r /usr/bin/free ]; then
    /usr/bin/free -t >$DUMP_DATE_DIR/free.dump 2>&1
    echo -e ".\c"
  fi
  if [ -r /usr/bin/sar ]; then
    /usr/bin/sar >$DUMP_DATE_DIR/sar.dump 2>&1
    echo -e ".\c"
  fi
  if [ -r /usr/bin/uptime ]; then
    /usr/bin/uptime >$DUMP_DATE_DIR/uptime.dump 2>&1
    echo -e ".\c"
  fi

  echo "OK!"
  echo "DUMP: $DUMP_DATE_DIR"
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case $COMMAND in
"start")
  start
  ;;
"stop")
  stop
  ;;
"status")
  status
  ;;
"restart")
  restart
  ;;
"dump")
  dump
  ;;
"info")
  info
  ;;
"help")
  help
  ;;
*)
  usage
  ;;
esac
exit 0

