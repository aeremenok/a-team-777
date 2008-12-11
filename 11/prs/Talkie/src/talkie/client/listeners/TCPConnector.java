package talkie.client.listeners;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import talkie.client.Client;
import talkie.common.constants.Message;

public class TCPConnector
    extends ClientConnector
{
    private Socket           socket;
    private DataOutputStream ostream;
    private DataInputStream  istream;

    public TCPConnector(
        Client client )
    {
        super( client );
    }

    @Override
    public void close()
    {
        try
        {
            istream.close();
        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();
        }
        try
        {
            ostream.close();
        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();
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
    public boolean establishConnection()
    {
        try
        {
            socket = new Socket( serverName, 7778 );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            valid = false;
            return valid;
        }
        try
        {
            ostream = new DataOutputStream( socket.getOutputStream() );
        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();
        }
        try
        {
            istream = new DataInputStream( socket.getInputStream() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        String s = Message.LOGIN + " " + login + " " + pass;
        try
        {
            ostream.writeUTF( s );
            ostream.flush();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            return valid;
        }
        String response = "";
        try
        {
            response = istream.readUTF();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            return valid;
        }

        if ( response.length() > 0 )
        {
            valid = Boolean.parseBoolean( response );
        }

        return valid;
    }

    @Override
    public void send(
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

    @Override
    protected void mainLoopStep()
    {
        String msg = "";
        try
        {
            msg = istream.readUTF();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        process( msg );
    }
}
