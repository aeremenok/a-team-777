package talkie.client.process;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import talkie.client.Client;
import talkie.client.connect.UDPConnection;
import talkie.common.constants.Message;
import talkie.common.constants.Talkie;

public class ClientListener
    implements
        Runnable
{
    private final Client   client;
    private DatagramSocket socket;
    private String         login;
    private String         pass;

    public ClientListener(
        Client client )
    {
        this.client = client;

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

    public boolean attemptToLogin()
    {
        client.getLoginDialog().lock();

        boolean result = establishConnection();

        if ( result )
        {
            client.getLoginDialog().unlock();
            client.getLoginDialog().setVisible( false );
            client.setTitle( login );
            client.display();
            return true;
        }
        else
        {
            client.getLoginDialog().unlock();
            client.getLoginDialog().getFailedLabel().setVisible( true );
            return false;
        }
    }

    public boolean establishConnection()
    {
        boolean result = false;

        String s = Message.LOGIN + " " + login + " " + pass;
        byte[] bytes = s.getBytes();

        // послылаем сообщение диспетчеру

        InetAddress dispatch = null;
        try
        {
            dispatch = InetAddress.getByName( "localhost" );
        }
        catch ( UnknownHostException e1 )
        {
            System.out.println( "no server found with name '" + "localhost" + "'" );
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
            UDPConnection connection = (UDPConnection) client.getConnection();
            connection.setAddress( inPacket.getAddress() );
            connection.setPort( inPacket.getPort() );
            data = inPacket.getData();

            String sData = new String( inPacket.getData(), 0, inPacket.getLength() );
            StringTokenizer st = new StringTokenizer( sData, " " );
            if ( st.countTokens() == 0 )
            {
                return false;
            }

            return Boolean.parseBoolean( st.nextToken() );
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
        while ( !Thread.currentThread().isInterrupted() )
        {
            try
            {
                byte[] data = new byte[Talkie.MSG_SIZE];
                DatagramPacket inPacket = new DatagramPacket( data, data.length );
                socket.receive( inPacket );

                String msg = new String( inPacket.getData(), 0, inPacket.getLength() );
                System.out.println( "client received '" + msg + "' from server" );
                synchronized ( client )
                {
                    processMsg( msg );
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void setLoginAndPass(
        String login,
        String pass )
    {
        this.login = login;
        this.pass = pass;
    }

    private void processMsg(
        String msg )
    {
        String content = client.getTextArea().getText();
        client.getTextArea().setText( content + msg + "\n----------\n" );
    }

    @Override
    protected void finalize()
        throws Throwable
    {
        socket.close();
        super.finalize();
    }
}
