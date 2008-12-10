package talkie.client.listeners;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import talkie.client.Client;
import talkie.client.speakers.UDPSpeaker;
import talkie.common.constants.Message;
import talkie.common.constants.Talkie;

public class UDPListener
    extends ClientListener
{
    DatagramSocket socket;

    public UDPListener(
        Client client )
    {
        super( client );

        try
        {
            socket = new DatagramSocket();
        }
        catch ( SocketException e )
        {
            System.out.println( "Unable to start listener for client" );
            e.printStackTrace();
            System.exit( 0 );
        }
    }

    public void close()
    {
        socket.close();
    }

    @Override
    public boolean establishConnection()

    {
        boolean result = false;

        String s = Message.LOGIN + " " + login + " " + pass;
        byte[] bytes = s.getBytes();

        // посылаем сообщение диспетчеру
        InetAddress dispatch = null;
        try
        {
            dispatch = InetAddress.getByName( serverName );
        }
        catch ( UnknownHostException e1 )
        {
            System.out.println( "no server found with name '" + serverName + "'" );
            return false;
        }
        DatagramPacket outPacket = new DatagramPacket( bytes, bytes.length, dispatch, 7777 );
        try
        {
            socket.send( outPacket );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        // принимаем ответ
        byte[] data = new byte[Talkie.MSG_SIZE];
        DatagramPacket inPacket = new DatagramPacket( data, data.length );

        try
        {
            int soTimeout = socket.getSoTimeout();
            socket.setSoTimeout( 5000 );
            socket.receive( inPacket );
            socket.setSoTimeout( soTimeout );

            // ответ принят, сохраняем параметры сокета, с которым будем общаться дальше
            UDPSpeaker connection = (UDPSpeaker) getClient().getSpeaker();
            connection.setAddress( inPacket.getAddress() );
            connection.setPort( inPacket.getPort() );
            data = inPacket.getData();

            String sData = new String( inPacket.getData(), 0, inPacket.getLength() );
            StringTokenizer st = new StringTokenizer( sData, " " );

            if ( st.countTokens() != 0 )
            {
                result = Boolean.parseBoolean( st.nextToken() );
            }

            if ( result )
            {
                connection.setActive( true );
            }

            return result;
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return result;
    }

    public DatagramSocket getSocket()
    {
        return socket;
    }

    public void run()
    {
        isRunning = true;
        while ( !Thread.currentThread().isInterrupted() )
        {
            try
            {
                byte[] data = new byte[Talkie.MSG_SIZE];
                DatagramPacket inPacket = new DatagramPacket( data, data.length );
                socket.receive( inPacket );

                String msg = new String( inPacket.getData(), 0, inPacket.getLength() );
                synchronized ( getClient() )
                {
                    processMsg( msg );
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        isRunning = false;
    }
}
