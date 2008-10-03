package talkie.connect;

import java.util.ArrayList;

import talkie.constants.Talkie;

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
        return establish( s.getBytes() );
    }

    /**
     * ���������� ���������
     * 
     * @param text
     */
    public void splitAndSend(
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

    /**
     * ��������� ����������
     * 
     * @param bytes
     */
    abstract protected boolean establish(
        byte[] bytes );

    /**
     * @return ����� � �������
     */
    abstract protected String receive();

    /**
     * @param millis ������������ ���� ��������
     * @return ����� � �������
     */
    abstract protected String receive(
        int millis );

    /**
     * ��������� ������ ���� (������� �� ����������)
     * 
     * @param bytes
     */
    abstract protected void send(
        byte[] bytes );
}
