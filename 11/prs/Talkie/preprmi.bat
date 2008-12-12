cd bin/
set TEMP=CLASSPATH
set CLASSPATH=.
rmic -v1.2 talkie.server.process.dispatchers.rmi.TalkieServerImpl
rmic -v1.2 talkie.client.connectors.rmi.TalkieClientImpl
set CLASSPATH=TEMP
cd ..