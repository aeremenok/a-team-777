cd bin/
set TMP=CLASSPATH
set CLASSPATH=.;..\lib\*.jar;CLASSPATH
java talkie.client.Client
set CLASSPATH=TMP
cd ..