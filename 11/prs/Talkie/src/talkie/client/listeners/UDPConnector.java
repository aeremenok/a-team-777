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

public class UDPConnector
    extends ClientConnector
{
    private DatagramSocket socket;
    private UDPSpeaker     speaker;

    public UDPConnector(
        Client client )
    {
        super( client );

        try
        {
            socket = new DatagramSocket();
            speaker = new UDPSpeaker();
        }
        catch ( SocketException e )
        {
            e.printStackTrace();
            System.exit( 0 );
        }
    }

    @Override
    public void close()
    {
        send( Message.LOGOUT );
        speaker.close();
        socket.close();
        Thread.currentThread().interrupt();
    }

    @Override
    public boolean establishConnection()
    {
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
            valid = false;
            return valid;
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
            speaker.setAddress( inPacket.getAddress() );
            speaker.setPort( inPacket.getPort() );
            data = inPacket.getData();

            String sData = new String( inPacket.getData(), 0, inPacket.getLength() );
            StringTokenizer st = new StringTokenizer( sData, " " );

            if ( st.countTokens() != 0 )
            {
                valid = Boolean.parseBoolean( st.nextToken() );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    @Override
    public void send(
        String message )
    {
        speaker.send( message );
    }

    @Override
    protected void mainLoopStep()
    {
        try
        {
            byte[] data = new byte[Talkie.MSG_SIZE];
            DatagramPacket inPacket = new DatagramPacket( data, data.length );
            socket.receive( inPacket );

            String msg = new String( inPacket.getData(), 0, inPacket.getLength() );
            synchronized ( getClient() )
            {
                process( msg );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
