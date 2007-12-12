package ru.spb._3352.tftp.common;

/**
 * оюйер ондрбепфдемхъ днонкмхрекэмшу ножхи (OPCODE = 6) FIXME ДНАЮБХРЭ
 * ОНДДЕПФЙС ПЮГЛЕПЮ ОЮЙЕРЮ
 */
public class OACK
    extends ACK
{

    /**
     * йнд ноепюжхх ондрбепфдемхъ ножхи ( 6 )
     */
    static public final int OPCODE  = 6;

    /**
     * ондрбепфдюелше ножхх
     */
    private TFTPOptions     options = null;

    /**
     * йнмярпсйрнп
     * 
     * @param tftpP оюйер
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
     * йнмярпсйрнп
     * 
     * @param blockNr мнлеп акнйю
     * @param options ножхх
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

        // йнд ноепюжхх
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // ножхх
        int IX_OPTION = IX_OPCODE + 2;

        // рюилюср
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

        // пюглеп тюикю
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

        // мнлеп акнйю
        tftpP[IX_BLOCKNR] = (byte) ((blockNr >> 8) & 0xff);
        tftpP[IX_BLOCKNR + 1] = (byte) (blockNr & 0xff);

        return tftpP;
    }

    /**
     * пюяявхршбюер дкхмс оюйерю б аюирюу
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
     * бнгбпюыюер рюилюср
     * 
     * @return рюилюср
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
     * пюглеп тюикю
     * 
     * @return пюглеп тюикю
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
     * япедх ножхи еярэ рюилюср
     * 
     * @return TRUE еякх рюилюср еярэ япедх ножхи, FALSE хмюве
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
     * япедх ножхи еярэ пюглеп тюикю
     * 
     * @return TRUE, еякх еярэ ножхъ "пюглеп тюикю", FALSE хмюве
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
