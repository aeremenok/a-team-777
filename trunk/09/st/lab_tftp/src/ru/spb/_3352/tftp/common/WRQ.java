package ru.spb._3352.tftp.common;

/**
 * ����� ������� �� ������ (OPCODE = 2)
 */
public class WRQ
    extends FRQ
{
    /**
     * ��� ������� �� ������ ( 2 )
     */
    static public final int OPCODE = 2;

    /**
     * �����������
     * 
     * @throws InstantiationException
     */
    public WRQ()
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
    public WRQ(
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
