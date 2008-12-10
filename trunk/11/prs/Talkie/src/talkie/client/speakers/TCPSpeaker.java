package talkie.client.speakers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSpeaker
    extends ClientSpeaker
{
    private Socket           socket;
    private DataOutputStream ostream;
    private DataInputStream  istream;

    public TCPSpeaker(
        InetAddress address,
        int port )
        throws IOException
    {
        socket = new Socket( address, port );
        ostream = new DataOutputStream( socket.getOutputStream() );
        istream = new DataInputStream( socket.getInputStream() );
    }

    @Override
    public void close()
    {
        try
        {
            istream.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        try
        {
            ostream.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        try
        {
            socket.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected String receive()
    {
        String result = "";
        try
        {
            result = istream.readUTF();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected String receive(
        int millis )
    {
        String result = "";
        try
        {
            int soTimeout = socket.getSoTimeout();
            socket.setSoTimeout( millis );
            result = istream.readUTF();
            socket.setSoTimeout( soTimeout );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void send(
        String message )
    {
        try
        {
            ostream.writeUTF( message );
            ostream.flush();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
