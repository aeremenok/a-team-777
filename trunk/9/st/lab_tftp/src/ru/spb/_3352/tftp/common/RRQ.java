package ru.spb._3352.tftp.common;

/**
 * ����� ������� �� ������ (OPCODE = 1)
 */
public class RRQ
    extends FRQ
{
    /**
     * ��� �������� ������� �� ������ ( 1 )
     */
    static public final int OPCODE = 1;

    /**
     * �����������
     */
    public RRQ()
        throws InstantiationException
    {
        super();
    }

    /**
     * �����������
     * 
     * @param tftpP UDP-�����
     * @throws InstantiationException
     */
    public RRQ(
        byte[] tftpP )
        throws InstantiationException
    {
        super( tftpP );
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.TFTPPacket#getOpCode()
     */
    public int getOpCode()
    {
        return OPCODE;
    }
}
