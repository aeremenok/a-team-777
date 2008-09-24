package ru.spb._3352.tftp.common;

/**
 * оюйер ньхайх (OPCODE = 5)
 */
public class ERROR
    extends TFTPPacket
{
    /**
     * ньхайю "мер днярсою"
     */
    public final static int ERR_ACCESS_VIOLATION = 2;

    /**
     * ньхайю "дхяй гюонкмем"
     */
    public final static int ERR_DISK_FULL        = 3;

    /**
     * ньхайю "тюик сфе ясыеярбсер"
     */
    public final static int ERR_FILE_EXISTS      = 6;

    /**
     * ньхайю "тюик ме мюидем"
     */
    public final static int ERR_FILE_NOT_FOUND   = 1;

    /**
     * ньхайю "медносярхлюъ ноепюжхъ"
     */
    public final static int ERR_ILLEGAL_OP       = 4;

    /**
     * ньхайю "мер ньхайх" =)
     */
    public final static int ERR_NO_ERROR         = -1;

    /**
     * ньхайю "мер онкэгнбюрекъ"
     */
    public final static int ERR_NO_SUCH_USER     = 7;

    /**
     * ньхайю "ме ноепедекемю"
     */
    public final static int ERR_NOT_DEFINED      = 0;

    /**
     * ньхайю "мехгбеярмши онпр"
     */
    public final static int ERR_UNKNOWN_TRANS_ID = 5;

    /**
     * рейярнбше нохяюмхъ ньханй
     */
    final static String[]   errStrings           =
                                                   { "Unknown error", "File not found", "Access violation",
                    "Disk full or allocation exceeded", "Illegal TFTP operation", "Unknown transfer ID",
                    "File already exists", "No such user" };

    /**
     * онкнфемхе йндю ньхайх б люяяхбе аюир
     */
    final static int        IX_ERRCODE           = 2;

    /**
     * онкнфемхе яннаыемхъ на ньхайе б люяяхбе аюир
     */
    final static int        IX_ERRMSG            = 4;

    /**
     * лхмхлюкэмши пюглеп оюйерю
     */
    static public final int MIN_PACKET_SIZE      = 5;

    /**
     * йнд ноепюжхх ньхайх ( 5 )
     */
    static public final int OPCODE               = 5;

    /**
     * бнгбпюыюер рейярнбне нохяюмхе ньхайх
     * 
     * @param messageID хдемрхтхйюрнп ньхайх
     * @return рейярнбне нохяюмхе ньхайх
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
     * йнд рейсыеи ньхайх
     */
    private int    errorCode    = ERR_NO_ERROR;

    /**
     * рейярнбне нохяюмхе рейсыеи ньхайх
     */
    private String errorMessage = "";

    /**
     * йнмярпсйрнп
     * 
     * @param tftpP UDP-оюйер
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
     * йнмярпсйрнп
     * 
     * @param code йнд ньхайх
     * @param message яннаыемхе на ньхайе
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

        // бярюбкъел йнд ноепюжхх
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // бярюбкъел йнд ньхайх
        tftpP[IX_ERRCODE] = (byte) ((errorCode >> 8) & 0xff);
        tftpP[IX_ERRCODE + 1] = (byte) (errorCode & 0xff);

        // бярюбкъел рейярнбне нохяюмхе ньхайх
        System.arraycopy( errorMessage.getBytes(), 0, tftpP, IX_ERRMSG, errorMessage.length() );
        tftpP[tftpLen - 1] = 0; // ярпнйю гюйюмвхбюеряъ мск╗л

        return tftpP;
    }

    /**
     * бнгбпюыюер йнд ньхайх
     * 
     * @return йнд ньхайх
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * бнгбпюыюер рейярнбне нохяюмхе ньхайх
     * 
     * @return рейярнбне нохяюмхе ньхайх
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
