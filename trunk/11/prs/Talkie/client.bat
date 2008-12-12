cd bin/
set TMP=CLASSPATH
set CLASSPATH=.;..\lib\*.jar;CLASSPATH
javaw talkie.client.Client
set CLASSPATH=TMP
cd ..