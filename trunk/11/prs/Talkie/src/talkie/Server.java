package talkie;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

import talkie.constants.Talkie;
import talkie.data.User;
import talkie.process.ClientHandler;

public class Server
{
    private HashMap<String, User> users = new HashMap<String, User>();

    {
        User root = new User( "root", "123" );
        User ssv = new User( "ssv", "123" );
        User eav = new User( "eav", "123" );
        User epa = new User( "epa", "123" );

        ssv.addFriend( eav );
        ssv.addFriend( epa );

        users.put( "root", root );
        users.put( "ssv", ssv );
        users.put( "eav", eav );
        users.put( "epa", epa );
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(
        String[] args )
    {
        if ( args.length != 1 )
        {
            System.err.println( "need port number" );
            System.exit( 0 );
        }

        Server server = new Server();
        int port = Integer.parseInt( args[0] );
        System.out.println( "Server started on port: " + port );

        DatagramSocket socket = null;
        try
        {
            socket = new DatagramSocket( port );
            while ( true )
            {
                byte[] inBuf = new byte[Talkie.MSG_SIZE];
                DatagramPacket inPacket = new DatagramPacket( inBuf, inBuf.length );
                socket.receive( inPacket );

                try
                {
                    ClientHandler target = new ClientHandler( server, inPacket );
                    new Thread( target ).start();
                }
                catch ( SocketException e )
                {
                    System.err.println( "Не удалось создать сокет для работы с новым клиентом" );
                    e.printStackTrace();
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            if ( socket != null )
            {
                socket.close();
            }
        }
    }

    public Server()
    {
    }

    public HashMap<String, User> getUsers()
    {
        return users;
    }
}
