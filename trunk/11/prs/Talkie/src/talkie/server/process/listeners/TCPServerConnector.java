package talkie.server.process.listeners;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import talkie.server.Server;

public class TCPServerConnector
    extends ServerConnector
{
    private DataOutputStream ostream = null;
    private DataInputStream  istream = null;
    private final Socket     socket;

    public TCPServerConnector(
        Server server,
        Socket socket )
    {
        super( server );
        this.socket = socket;
        try
        {
            this.ostream = new DataOutputStream( socket.getOutputStream() );
            this.istream = new DataInputStream( socket.getInputStream() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        valid = true;
    }

    @Override
    public void close()
    {
        super.close();
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

    @Override
    protected void send(
        String string )
    {
        try
        {
            ostream.writeUTF( string );
            ostream.flush();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
