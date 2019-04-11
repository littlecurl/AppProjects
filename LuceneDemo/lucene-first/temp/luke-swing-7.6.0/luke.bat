@echo off
set ADD_OPENS_OPTION=--add-opens java.base/java.lang=ALL-UNNAMED
java %ADD_OPENS_OPTION% -version > nul 2>&1
if %errorlevel% equ 0 (
    rem running on jdk9+
    set JAVA_OPTIONS=%ADD_OPENS_OPTION%
)

set JAVA_OPTIONS=%JAVA_OPTIONS% -Xmx1024m -Xms512m -XX:MaxMetaspaceSize=256m
start javaw %JAVA_OPTIONS% -Dswing.systemlaf=com.jgoodies.looks.plastic.PlasticXPLookAndFeel -jar .\target\luke-swing-with-deps.jar
