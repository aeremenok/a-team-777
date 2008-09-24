package ru.spb._3352.tftp.common;

/**
 * ����� ������ (OPCODE = 3)
 */
public class DATA
    extends ACK
{
    /**
     * ��������� ������ � ������� ����
     */
    final static int        IX_DATA = 4;

    /**
     * ��� �������� �������� ������ ( 3 )
     */
    static public final int OPCODE  = 3;

    /**
     * ������ ������
     */
    byte[]                  data    = {};

    /**
     * ����������� �� ���������
     */
    public DATA()
        throws InstantiationException
    {
        super();
    }

    /**
     * �����������
     * 
     * @param tftpP ������ ����
     * @param tftpPLength ����� ������� ����
     * @throws InstantiationException
     */
    public DATA(
        byte[] tftpP,
        int tftpPLength )
        throws InstantiationException
    {
        super( tftpP );

        try
        {
            if ( tftpPLength > tftpP.length )
            {
                tftpPLength = tftpP.length;
            }

            int len = tftpPLength - IX_DATA;
            data = new byte[len];
            System.arraycopy( tftpP, 4, data, 0, len );
        }
        catch ( Exception e )
        {
            throw new InstantiationException( "CODING ERROR? " + e.toString() );
        }
    }

    /**
     * �����������
     * 
     * @param blockNr ����� �����
     * @param data ������
     * @throws InstantiationException
     */
    public DATA(
        int blockNr,
        byte[] data )
        throws InstantiationException
    {
        this( blockNr, data, 0, data.length );
    }

    /**
     * �����������
     * 
     * @param blockNr ����� �����
     * @param data ������ ������
     * @param offset �������� � �������� �������
     * @param len ����� ������
     * @throws InstantiationException
     */
    public DATA(
        int blockNr,
        byte[] data,
        int offset,
        int len )
        throws InstantiationException
    {
        super( blockNr );

        try
        {
            this.data = new byte[len];
            System.arraycopy( data, offset, this.data, 0, len );
        }
        catch ( Exception e )
        {
            throw new InstantiationException( "Data passed to constructor of DATA is invalid! " + e.toString() );
        }
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.ACK#getBytes()
     */
    public byte[] getBytes()
    {
        int tftpLen = MIN_PACKET_SIZE;

        if ( data != null )
        {
            tftpLen += data.length;
        }

        byte[] tftpP = new byte[tftpLen];

        // ��������� ��� ��������
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // ��������� 2-� ��������
        tftpP[IX_BLOCKNR] = (byte) ((blockNr >> 8) & 0xff);
        tftpP[IX_BLOCKNR + 1] = (byte) (blockNr & 0xff);
        // Insert data
        System.arraycopy( data, 0, tftpP, IX_DATA, data.length );
        return tftpP;
    }

    /**
     * ���������� ������ ������
     * 
     * @return ������ ������
     */
    public byte[] getData()
    {
        return data;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.ACK#getOpCode()
     */
    public int getOpCode()
    {
        return OPCODE;
    }
}
