package talkie;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.StringTokenizer;

import talkie.data.User;

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

                String inMsg = new String( inPacket.getData() );

                StringTokenizer tokenizer = new StringTokenizer( inMsg, " " );
                if ( tokenizer.countTokens() > 0 )
                {
                    String operation = tokenizer.nextToken();

                    if ( Talkie.LOGIN.equalsIgnoreCase( operation ) )
                    {
                        boolean success = false;
                        String login = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
                        String pass = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

                        System.out.println( "login attempt detected, login='" + login + "', pass='" + pass + "'" );
                        User user = server.getUsers().get( login );
                        if ( user != null && pass.equals( user.getPass() ) )
                        {
                            success = true;
                        }

                        String outMsg = "" + success + " ";
                        System.out.println( "\tvalid: " + outMsg.toUpperCase() );

                        byte[] outBuf = outMsg.getBytes();
                        InetAddress clientAddress = inPacket.getAddress();
                        int clientPort = inPacket.getPort();
                        DatagramPacket outPacket =
                            new DatagramPacket( outBuf, outBuf.length, clientAddress, clientPort );

                        synchronized ( socket )
                        {
                            try
                            {
                                socket.send( outPacket );
                            }
                            catch ( IOException e )
                            {
                                e.printStackTrace();
                            }
                        }
                    }
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
