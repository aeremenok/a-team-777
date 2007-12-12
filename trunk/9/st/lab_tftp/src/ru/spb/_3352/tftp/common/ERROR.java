package ru.spb._3352.tftp.common;

/**
 * ����� ������ (OPCODE = 5)
 */
public class ERROR
    extends TFTPPacket
{
    /**
     * ������ "��� �������"
     */
    public final static int ERR_ACCESS_VIOLATION = 2;

    /**
     * ������ "���� ��������"
     */
    public final static int ERR_DISK_FULL        = 3;

    /**
     * ������ "���� ��� ����������"
     */
    public final static int ERR_FILE_EXISTS      = 6;

    /**
     * ������ "���� �� ������"
     */
    public final static int ERR_FILE_NOT_FOUND   = 1;

    /**
     * ������ "������������ ��������"
     */
    public final static int ERR_ILLEGAL_OP       = 4;

    /**
     * ������ "��� ������" =)
     */
    public final static int ERR_NO_ERROR         = -1;

    /**
     * ������ "��� ������������"
     */
    public final static int ERR_NO_SUCH_USER     = 7;

    /**
     * ������ "�� �����������"
     */
    public final static int ERR_NOT_DEFINED      = 0;

    /**
     * ������ "����������� ����"
     */
    public final static int ERR_UNKNOWN_TRANS_ID = 5;

    /**
     * ��������� �������� ������
     */
    final static String[]   errStrings           =
                                                   { "Unknown error", "File not found", "Access violation",
                    "Disk full or allocation exceeded", "Illegal TFTP operation", "Unknown transfer ID",
                    "File already exists", "No such user" };

    /**
     * ��������� ���� ������ � ������� ����
     */
    final static int        IX_ERRCODE           = 2;

    /**
     * ��������� ��������� �� ������ � ������� ����
     */
    final static int        IX_ERRMSG            = 4;

    /**
     * ����������� ������ ������
     */
    static public final int MIN_PACKET_SIZE      = 5;

    /**
     * ��� �������� ������ ( 5 )
     */
    static public final int OPCODE               = 5;

    /**
     * ���������� ��������� �������� ������
     * 
     * @param messageID ������������� ������
     * @return ��������� �������� ������
     */
    static String getErrorMessage(
        int messageID )
    {
        if ( messageID > ERR_NOT_DEFINED && messageID <= ERR_NO_SUCH_USER )
        {
            return errStrings[messageID];
        }
        else
        {
            return errStrings[ERR_NOT_DEFINED];
        }
    }

    /**
     * ��� ������� ������
     */
    private int    errorCode    = ERR_NO_ERROR;

    /**
     * ��������� �������� ������� ������
     */
    private String errorMessage = "";

    /**
     * �����������
     * 
     * @param tftpP UDP-�����
     * @throws InstantiationException
     */
    public ERROR(
        byte[] tftpP )
        throws InstantiationException
    {
        super( tftpP );

        errorCode = makeword( tftpP[IX_ERRCODE], tftpP[IX_ERRCODE + 1] );

        if ( IX_ERRMSG >= tftpP.length )
        {
            throw new InstantiationException( "Argument passed to constructor is not a complete "
                                              + getClass().getName() + " packet!" );
        }

        try
        {
            StringBuffer sb = new StringBuffer();

            for ( int i = IX_ERRMSG; i < tftpP.length; i++ )
            {
                if ( tftpP[i] == 0 )
                {
                    break;
                }

                sb.append( (char) tftpP[i] );
            }

            errorMessage = sb.toString();
        }
        catch ( Exception e )
        {
            throw new InstantiationException( "CODING ERROR? " + e.toString() );
        }
    }

    /**
     * �����������
     * 
     * @param code ��� ������
     * @param message ��������� �� ������
     * @throws InstantiationException
     */
    public ERROR(
        int code,
        String message )
        throws InstantiationException
    {
        super();
        errorCode = code;
        errorMessage = message;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.TFTPPacket#getBytes()
     */
    public byte[] getBytes()
    {
        int tftpLen = MIN_PACKET_SIZE + errorMessage.length() + 1;
        byte[] tftpP = new byte[tftpLen];

        // ��������� ��� ��������
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // ��������� ��� ������
        tftpP[IX_ERRCODE] = (byte) ((errorCode >> 8) & 0xff);
        tftpP[IX_ERRCODE + 1] = (byte) (errorCode & 0xff);

        // ��������� ��������� �������� ������
        System.arraycopy( errorMessage.getBytes(), 0, tftpP, IX_ERRMSG, errorMessage.length() );
        tftpP[tftpLen - 1] = 0; // ������ ������������� ��˨�

        return tftpP;
    }

    /**
     * ���������� ��� ������
     * 
     * @return ��� ������
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * ���������� ��������� �������� ������
     * 
     * @return ��������� �������� ������
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.TFTPPacket#getMinPacketSize()
     */
    public int getMinPacketSize()
    {
        return MIN_PACKET_SIZE;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.TFTPPacket#getOpCode()
     */
    public int getOpCode()
    {
        return OPCODE;
    }
}
