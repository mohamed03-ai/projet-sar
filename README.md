server
cd server
javac *.java
rmic -v1.1 GameServer
rmic -v1.1 FabGameImpl
java -Djava.security.policy=server.policy     -Djava.rmi.server.codebase=http://localhost/www/      ServersideMain


client
cd ../client
cp ../server/FabGameImpl_Stub.class .
cp ../server/GameServer_Stub.class .
cp ../server/FabGameInterface.class .
cp ../server/GameInterface.class .
javac *.java
java -Djava.security.policy=client.policy      -Djava.rmi.server.codebase=http://localhost/www/      clientsideMain


