start rmiregistry
start tnameserv -ORBInitialPort 1050

cd bin/
java -classpath .;..\lib\log4j-1.2.15.jar -Djava.rmi.server.codebase=file://localhost/d:/DOCUMENTS/Projects/a-team-777/11/prs/Talkie/bin/ talkie.server.Server
cd ..