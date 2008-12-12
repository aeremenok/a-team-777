cd bin/
set TMP=CLASSPATH
set CLASSPATH=.;..\lib\log4j-1.2.15.jar;CLASSPATH
java talkie.server.Server
set CLASSPATH=TMP
cd ..