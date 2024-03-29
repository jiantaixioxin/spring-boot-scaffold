echo off
set APP_NAME=${project.build.finalName}.jar
set LOG_IMPL_FILE=log4j2.xml
set ENV=it

set PROJECT_DIR=%~dp0
set LOGGING_CONFIG=
if exist %PROJECT_DIR%/config/%LOG_IMPL_FILE% (
    set LOGGING_CONFIG=-Dlogging.config=%PROJECT_DIR%/config/%LOG_IMPL_FILE%
)
set CONFIG= %LOGGING_CONFIG% -Dspring.config.location=%PROJECT_DIR%/config/ -Dspring.profiles.active=%ENV%

set DEBUG_OPTS=
if ""%1"" == ""debug"" (
   set DEBUG_OPTS= -Xloggc:../logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs
   goto debug
)

set JMX_OPTS=
if ""%1"" == ""jmx"" (
   set JMX_OPTS= -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9888 -Dcom.sun.management.jmxremote.ssl=FALSE -Dcom.sun.management.jmxremote.authenticate=FALSE
   goto jmx
)

echo "Starting the %APP_NAME%"
java -Xms512m -Xmx512m -server %DEBUG_OPTS% %JMX_OPTS% %CONFIG% -jar %PROJECT_DIR%/libs/%APP_NAME%
echo "java -Xms512m -Xmx512m -server %DEBUG_OPTS% %JMX_OPTS% %CONFIG% -jar %PROJECT_DIR%/libs/%APP_NAME%"
goto end

:debug
echo "debug"
java -Xms512m -Xmx512m -server %DEBUG_OPTS% %CONFIG% -jar %PROJECT_DIR%/libs/%APP_NAME%
goto end

:jmx
java -Xms512m -Xmx512m -server %JMX_OPTS% %CONFIG% -jar %PROJECT_DIR%/libs/%APP_NAME%
goto end

:end
pause