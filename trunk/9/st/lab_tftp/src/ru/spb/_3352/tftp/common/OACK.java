package ru.spb._3352.tftp.common;

/**
 * ����� ������������� �������������� ����� (OPCODE = 6) FIXME ��������
 * ��������� ������� ������
 */
public class OACK
    extends ACK
{

    /**
     * ��� �������� ������������� ����� ( 6 )
     */
    static public final int OPCODE  = 6;

    /**
     * �������������� �����
     */
    private TFTPOptions     options = null;

    /**
     * �����������
     * 
     * @param tftpP �����
     * @throws InstantiationException
     */
    public OACK(
        byte[] tftpP )
        throws InstantiationException
    {
        super( tftpP );

        int IX_OPTION = IX_OPCODE + 2;

        while ( IX_OPTION < tftpP.length - 2 )
        {
            IX_OPTION = StrUtil.readOption( this, IX_OPTION, tftpP, options );
        }

        blockNr = 0;
    }

    /**
     * �����������
     * 
     * @param blockNr ����� �����
     * @param options �����
     */
    public OACK(
        int blockNr,
        TFTPOptions options )
    {
        super( blockNr );
        this.options = options;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.ACK#getBytes()
     */
    public byte[] getBytes()
    {
        byte[] tftpP = new byte[getLength()];

        // ��� ��������
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // �����
        int IX_OPTION = IX_OPCODE + 2;

        // �������
        int timeout = options.getTimeout();
        if ( timeout > 0 )
        {
            int length = options.TIMEOUT.length();
            System.arraycopy( options.TIMEOUT.getBytes(), 0, tftpP, IX_OPTION, length );
            IX_OPTION += length;
            tftpP[IX_OPTION++] = 0;

            String timeoutValue = (String) options.get( options.TIMEOUT );
            length = timeoutValue.length();
            System.arraycopy( timeoutValue.getBytes(), 0, tftpP, IX_OPTION, length );
            IX_OPTION += length;
            tftpP[IX_OPTION++] = 0;
        }

        // ������ �����
        int tsize = options.getTransferSize();
        if ( tsize > 0 )
        {
            int length = options.TSIZE.length();
            System.arraycopy( options.TSIZE.getBytes(), 0, tftpP, IX_OPTION, length );
            IX_OPTION += length;
            tftpP[IX_OPTION++] = 0;

            String tsizeValue = (String) options.get( options.TSIZE );
            length = tsizeValue.length();
            System.arraycopy( tsizeValue.getBytes(), 0, tftpP, IX_OPTION, length );
            IX_OPTION += length;
            tftpP[IX_OPTION++] = 0;
        }
        int IX_BLOCKNR = IX_OPTION;

        // ����� �����
        tftpP[IX_BLOCKNR] = (byte) ((blockNr >> 8) & 0xff);
        tftpP[IX_BLOCKNR + 1] = (byte) (blockNr & 0xff);

        return tftpP;
    }

    /**
     * ������������ ����� ������ � ������
     */
    private int getLength()
    {
        int length = 4;

        if ( options == null )
        {
            return length;
        }

        int timeout = options.getTimeout();
        if ( timeout > 0 )
        {
            length += options.TIMEOUT.length() + ((String) options.get( options.TIMEOUT )).length() + 2;
        }

        int tsize = options.getTransferSize();
        if ( tsize > 0 )
        {
            length += options.TSIZE.length() + ((String) options.get( options.TSIZE )).length() + 2;
        }

        return length;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.ACK#getOpCode()
     */
    public int getOpCode()
    {
        return OPCODE;
    }

    /**
     * ���������� �������
     * 
     * @return �������
     */
    public int getTimeout()
    {
        if ( options != null )
        {
            return options.getTimeout();
        }

        return -1;
    }

    /**
     * ������ �����
     * 
     * @return ������ �����
     */
    public int getTransferSize()
    {
        if ( options != null )
        {
            return options.getTransferSize();
        }

        return -1;
    }

    /**
     * ����� ����� ���� �������
     * 
     * @return TRUE ���� ������� ���� ����� �����, FALSE �����
     */
    public boolean hasTimeout()
    {
        if ( options != null )
        {
            return options.hasOption( TFTPOptions.TIMEOUT );
        }

        return false;
    }

    /**
     * ����� ����� ���� ������ �����
     * 
     * @return TRUE, ���� ���� ����� "������ �����", FALSE �����
     */
    public boolean hasTransferSize()
    {
        if ( options != null )
        {
            return options.hasOption( TFTPOptions.TSIZE );
        }

        return false;
    }
}
