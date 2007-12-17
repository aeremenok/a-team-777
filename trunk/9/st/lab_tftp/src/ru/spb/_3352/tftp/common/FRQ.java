package ru.spb._3352.tftp.common;

/**
 * ����������� ����� ��� �������� ������ � ������, �������������� �����
 */
public abstract class FRQ
    extends TFTPPacket
{
    /**
     * ����� ASCII
     */
    final static int        ASCII_MODE   = 1;

    /**
     * ��������� ����� ����� � ������
     */
    final static int        IX_FILENAME  = 2;

    /**
     * ����� ����� (�����٨�)
     */
    final static int        MAIL_MODE    = 3;

    /**
     * ��������� �������� ������� ��������
     */
    final static String[]   modeStrings  = { "unknown", "netascii", "octet", "mail" };

    /**
     * �������� �����
     */
    public final static int OCTET_MODE   = 2;

    /**
     * ����������� �����
     */
    final static int        UNKNOWN_MODE = 0;

    /**
     * �������� ������ ������ ��������
     * 
     * @param mode ����� ������
     * @return ������ ������ ��������
     */
    static String getModeString(
        int mode )
    {
        if ( mode > UNKNOWN_MODE && mode <= MAIL_MODE )
        {
            return modeStrings[mode];
        }
        else
        {
            return modeStrings[UNKNOWN_MODE];
        }
    }

    /**
     * ������� ��� �����
     */
    private String fileName    = "";

    /**
     * ������� ����� ��������
     */
    private int    mode        = UNKNOWN_MODE;

    /**
     * ������ ����� ��� ��������� � ������, null ���� ����� �� ������������
     */
    TFTPOptions    tftpOptions = null;

    /**
     * ����������� �� ���������
     */
    public FRQ()
    {
        super();
    }

    /**
     * �����������
     * 
     * @param tftpP UDP-�����
     * @throws InstantiationException
     */
    public FRQ(
        byte[] tftpP )
        throws InstantiationException
    {
        super( tftpP );

        // �������� ��� ��������� ���������� � ���������� ��� ��������
        try
        {
            int IX_MODE = readFileName( tftpP );
            int IX_OPTION = readMode( IX_MODE, tftpP );

            // ��������� �� �������������� �����
            if ( IX_OPTION >= tftpP.length )
            {
                return;
            }

            // ��������� �������������� �����
            while ( IX_OPTION < tftpP.length )
            {
                IX_OPTION = StrUtil.readOption( this, IX_OPTION, tftpP, tftpOptions );
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InstantiationException )
            {
                throw (InstantiationException) t;
            }
            throw new InstantiationException( "CODING ERROR? " + t.getMessage() );
        }
    }

    /**
     * ���� ����� ������������ ��� ��������� ������� ����, ���������������
     * ������ �� ������ ��� ������. ��� �������� (OPCODE) ��������������� �
     * ������-����������. � ��������� ���� ���� ����� �� ������������, �.�.
     * ������� �� ������ � ������ ����� ���������� � ����������� ������ ��
     * �������� ���� (UDP-�������).
     */
    public byte[] getBytes()
    {
        String modeString = "";

        if ( mode >= ASCII_MODE && mode <= MAIL_MODE )
        {
            modeString = modeStrings[mode];
        }

        // ��������� ����� ������
        int tftpLen = 2 + fileName.length() + 1 + modeString.length() + 1;

        if ( hasOptions() )
        {
            tftpLen += tftpOptions.getOptionsSize();
        }

        // ������� �����
        byte[] tftpP = new byte[tftpLen];

        // ��������� ��� ��������
        tftpP[IX_OPCODE] = (byte) ((this.getOpCode() >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (this.getOpCode() & 0xff);

        // ��������� ��� �����
        System.arraycopy( fileName.getBytes(), 0, tftpP, IX_FILENAME, fileName.length() );

        // ��������� ������ ������
        int IX_MODE = IX_FILENAME + fileName.length();
        tftpP[IX_MODE++] = 0; // ����������� ������ � ������ ����� ��˨�
        System.arraycopy( modeString.getBytes(), 0, tftpP, IX_MODE, modeString.length() );
        IX_MODE += modeString.length();
        tftpP[IX_MODE] = 0; // ����������� ������ ������ ��˨�

        int IX_OPTION = IX_MODE + 1;
        int optionLength = 0;
        String optionValue = null;

        // �������������� ����� �������
        if ( hasOptions() && tftpOptions.hasOption( TFTPOptions.TIMEOUT ) )
        {
            optionLength = TFTPOptions.TIMEOUT.length();
            System.arraycopy( TFTPOptions.TIMEOUT.getBytes(), 0, tftpP, IX_OPTION, optionLength );
            IX_OPTION += optionLength;
            tftpP[IX_OPTION++] = 0;

            optionValue = "" + tftpOptions.getTimeout();
            optionLength = optionValue.length();
            System.arraycopy( optionValue.getBytes(), 0, tftpP, IX_OPTION, optionLength );
            IX_OPTION += optionLength;
            tftpP[IX_OPTION++] = 0;
        }

        // �������������� ����� ������� �����
        if ( hasOptions() && tftpOptions.hasOption( TFTPOptions.TSIZE ) )
        {
            optionLength = TFTPOptions.TSIZE.length();
            System.arraycopy( TFTPOptions.TSIZE.getBytes(), 0, tftpP, IX_OPTION, optionLength );
            IX_OPTION += optionLength;
            tftpP[IX_OPTION++] = 0;

            optionValue = "" + tftpOptions.getTransferSize();
            optionLength = optionValue.length();
            System.arraycopy( optionValue.getBytes(), 0, tftpP, IX_OPTION, optionLength );
            IX_OPTION += optionLength;
            tftpP[IX_OPTION++] = 0;
        }

        // �������������� ����� ������� �����
        if ( hasOptions() && tftpOptions.hasOption( TFTPOptions.BLKSIZE ) )
        {
            optionLength = TFTPOptions.BLKSIZE.length();
            System.arraycopy( TFTPOptions.BLKSIZE.getBytes(), 0, tftpP, IX_OPTION, optionLength );
            IX_OPTION += optionLength;
            tftpP[IX_OPTION++] = 0;

            optionValue = "" + tftpOptions.getBlockSize();
            optionLength = optionValue.length();
            System.arraycopy( optionValue.getBytes(), 0, tftpP, IX_OPTION, optionLength );
            IX_OPTION += optionLength;
            tftpP[IX_OPTION++] = 0;
        }

        return tftpP;
    }

    /**
     * ���������� ��� �����
     * 
     * @return ��� �����
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * ���������� ��� ������
     * 
     * @return ��� ������
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * ���������� �������
     * 
     * @return �������
     */
    public int getTimeout()
    {
        if ( hasOptions() )
        {
            return tftpOptions.getTimeout();
        }

        return -1;
    }

    /**
     * ���������� ������ �����
     * 
     * @return ������ �����
     */
    public int getBlockSize()
    {
        if ( hasOptions() )
        {
            return tftpOptions.getBlockSize();
        }

        return -1;
    }

    /**
     * ���������� ������ �����
     * 
     * @return ������ �����
     */
    public int getTransferSize()
    {
        if ( hasOptions() )
        {
            return tftpOptions.getTransferSize();
        }
        return -1;
    }

    /**
     * ��������� �� ������� �������������� �����
     * 
     * @return TRUE ���� �������������� ����� ����, FALSE �����
     */
    public boolean hasOptions()
    {
        return (tftpOptions != null);
    }

    /**
     * ������ ����� �� ������� ����
     * 
     * @param b ������ ����, ���������� ��� �����
     * @return ������ � ������� ����� �����ب����� ��˨� ����� �����
     * @throws InstantiationException ����� �����
     */
    private int readFileName(
        byte[] b )
        throws InstantiationException
    {
        // ��������� �� ����� �� ������� �������
        if ( IX_FILENAME >= b.length )
        {
            throw new InstantiationException( "TFTP request passed to constructor is not a complete "
                                              + getClass().getName() + " packet! It does not contain the filename!" );
        }

        StringBuffer sb = new StringBuffer();

        // ������ ��� ����� � �������� ������ ������ ������ �������� � �������
        int IX_MODE = StrUtil.readString( IX_FILENAME, b, sb );
        fileName = sb.toString();

        return IX_MODE;
    }

    /**
     * ������ ������ �������� �� ������� ����
     * 
     * @param IX_MODE ������ ������ �������� ����-������
     * @param b ������ ���� ������
     * @return ������ ������� ����� �������������� ��˨� ������ ������ ��������
     * @throws InstantiationException ����� �����
     */
    private int readMode(
        int IX_MODE,
        byte[] b )
        throws InstantiationException
    {
        // ��������� �� ����� �� ������� �������
        if ( IX_MODE >= b.length )
        {
            throw new InstantiationException( "TFTP packet passed to constructor is not a complete "
                                              + getClass().getName() + " packet! Mode could not be found" );
        }

        // ������ ������ ������
        StringBuffer sb = new StringBuffer();
        int IX_OPTION = StrUtil.readString( IX_MODE, b, sb );
        String modeString = sb.toString();

        // ��������� ����� �������� �� ��������� ������
        for ( int i = ASCII_MODE; i <= MAIL_MODE; i++ )
        {
            if ( modeString.compareToIgnoreCase( getModeString( i ) ) == 0 )
            {
                mode = i;
                return IX_OPTION;
            }
        }

        // ���������������� ����� ��������
        throw new InstantiationException( "Unsupported mode found in the file request for mode: " + modeString );
    }

    /**
     * ������������� ��� �����
     * 
     * @param fileName ��� �����
     */
    public void setFileName(
        String fileName )
    {
        this.fileName = fileName;
    }

    /**
     * ������������� ����� ��������
     * 
     * @param mode ����� ��������
     */
    public void setMode(
        int mode )
    {
        this.mode = mode;
    }

    /**
     * ������������� �������
     * 
     * @param timeout �������
     */
    public void setTimeout(
        int timeout )
    {
        if ( tftpOptions == null )
        {
            tftpOptions = new TFTPOptions();
        }

        tftpOptions.setTimeout( timeout );
    }

    /**
     * ������������� ������ �����
     * 
     * @param transferSize ������ �����
     */
    public void setTransferSize(
        int transferSize )
    {
        if ( tftpOptions == null )
        {
            tftpOptions = new TFTPOptions();
        }

        tftpOptions.setTransferSize( transferSize );
    }

    /**
     * ������������� ������ �����
     * 
     * @param blkSize ������ �����
     */
    public void setBlockSize(
        int blkSize )
    {
        if ( tftpOptions == null )
        {
            tftpOptions = new TFTPOptions();
        }

        tftpOptions.setBlockSize( blkSize );
    }
}
