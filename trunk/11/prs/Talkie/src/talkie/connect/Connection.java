package talkie.connect;

import java.util.ArrayList;
import java.util.StringTokenizer;

import talkie.Talkie;

/**
 * ���������� ���������� ����� �������� � �������� Talkie
 * 
 * @author ssv
 */
public abstract class Connection
{
    /**
     * @param login ����� (��� ������������)
     * @param pass ������ ������������
     * @return true, ���� ������������ ����������, false - �����
     */
    public boolean login(
        String login,
        String pass )
    {
        String s = "l " + login + " " + pass + " ";
        send( s.getBytes() );

        String result = receive();
        if ( result == null )
        {
            return false;
        }
        StringTokenizer st = new StringTokenizer( result, " " );
        if ( st.countTokens() == 0 )
        {
            return false;
        }

        return Boolean.parseBoolean( st.nextToken() );
    }

    /**
     * ���������� ���������
     * 
     * @param text
     */
    public void process(
        String text )
    {
        byte[] buffer = text.getBytes();

        ArrayList<byte[]> msgs = new ArrayList<byte[]>();
        int fullMsgNumber = buffer.length / Talkie.MSG_SIZE;
        int lastMsgLen = buffer.length % Talkie.MSG_SIZE;
        int msgNumber = fullMsgNumber + (lastMsgLen > 0 ? 1 : 0);
        for ( int i = 0; i < msgNumber; i++ )
        {
            int length = i == msgNumber - 1 ? lastMsgLen : Talkie.MSG_SIZE;
            byte[] buf = new byte[length];
            System.arraycopy( buffer, i * Talkie.MSG_SIZE, buf, 0, length );
            msgs.add( buf );
        }

        for ( byte[] msg : msgs )
        {
            send( msg );
        }
    }

    abstract protected String receive();

    /**
     * ��������� ������ ���� (������� �� ����������)
     * 
     * @param bytes
     */
    abstract protected void send(
        byte[] bytes );
}
