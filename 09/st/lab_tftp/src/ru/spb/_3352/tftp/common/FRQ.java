package ru.spb._3352.tftp.common;

/**
 * юаярпюйрмши йкюяя дкъ гюопнянб времхъ х гюохях, онддепфхбючряъ ножхх
 */
public abstract class FRQ
    extends TFTPPacket
{
    /**
     * пефхл ASCII
     */
    final static int        ASCII_MODE   = 1;

    /**
     * онкнфемхе хлемх тюикю б оюйере
     */
    final static int        IX_FILENAME  = 2;

    /**
     * пефхл онврш (гюопеы╗м)
     */
    final static int        MAIL_MODE    = 3;

    /**
     * рейярнбше нохяюмхъ пефхлнб оепедювх
     */
    final static String[]   modeStrings  = { "unknown", "netascii", "octet", "mail" };

    /**
     * ахмюпмши пефхл
     */
    public final static int OCTET_MODE   = 2;

    /**
     * мехгбеярмши пефхл
     */
    final static int        UNKNOWN_MODE = 0;

    /**
     * онксвхрэ ярпнйс пефхлю оепедювх
     * 
     * @param mode мнлеп пефхлю
     * @return ярпнйю пефхлю оепедювх
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
     * рейсыее хлъ тюикю
     */
    private String fileName    = "";

    /**
     * рейсыхи пефхл оепедювх
     */
    private int    mode        = UNKNOWN_MODE;

    /**
     * ярпнйю ножхи дкъ бйкчвемхъ б гюопня, null еякх ножхх ме хяонкэгсчряъ
     */
    TFTPOptions    tftpOptions = null;

    /**
     * йнмярпсйрнп он слнквюмхч
     */
    public FRQ()
    {
        super();
    }

    /**
     * йнмярпсйрнп
     * 
     * @param tftpP UDP-оюйер
     * @throws InstantiationException
     */
    public FRQ(
        byte[] tftpP )
        throws InstantiationException
    {
        super( tftpP );

        // янахпюел бяе бнглнфмше хяйкчвемхъ б хяйкчвемхе опх янгдюмхх
        try
        {
            int IX_MODE = readFileName( tftpP );
            int IX_OPTION = readMode( IX_MODE, tftpP );

            // опнбепъел мю днонкмхрекэмше ножхх
            if ( IX_OPTION >= tftpP.length )
            {
                return;
            }

            // явхршбюел днонкмхрекэмше ножхх
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
     * щрнр лернд хяонкэгсеряъ дкъ онксвемхъ люяяхбю аюир, опедярюбкъчыецн
     * гюопня мю времхе хкх гюохяэ. йнд ноепюжхх (OPCODE) сярюмюбкхбюеряъ б
     * йкюяяе-мюякедмхйе. б яепбепмнл йнде щрнр лернд ме хяонкэгсеряъ, р.й.
     * гюопняш мю времхе х гюохяэ асдср онксвюрэяъ х янгдюбюрэяъ рнкэйн хг
     * люяяхбнб аюир (UDP-оюйернб).
     */
    public byte[] getBytes()
    {
        String modeString = "";

        if ( mode >= ASCII_MODE && mode <= MAIL_MODE )
        {
            modeString = modeStrings[mode];
        }

        // бшвхякъел дкхмс оюйерю
        int tftpLen = 2 + fileName.length() + 1 + modeString.length() + 1;

        if ( hasOptions() )
        {
            tftpLen += tftpOptions.getOptionsSize();
        }

        // янгдю╗л оюйер
        byte[] tftpP = new byte[tftpLen];

        // бярюбкъел йнд ноепюжхх
        tftpP[IX_OPCODE] = (byte) ((this.getOpCode() >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (this.getOpCode() & 0xff);

        // бярюбкъел хлъ тюикю
        System.arraycopy( fileName.getBytes(), 0, tftpP, IX_FILENAME, fileName.length() );

        // бярюбкъел ярпнйс пефхлю
        int IX_MODE = IX_FILENAME + fileName.length();
        tftpP[IX_MODE++] = 0; // гюйюмвхбюел ярпнйс я хлемел тюикю мск╗л
        System.arraycopy( modeString.getBytes(), 0, tftpP, IX_MODE, modeString.length() );
        IX_MODE += modeString.length();
        tftpP[IX_MODE] = 0; // гюйюмвхбюел ярпнйс пефхлю мск╗л

        int IX_OPTION = IX_MODE + 1;
        int optionLength = 0;
        String optionValue = null;

        // днонкмхрекэмюъ ножхъ рюилюср
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

        // днонкмхрекэмюъ ножхъ пюглепю тюикю
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

        // днонкмхрекэмюъ ножхъ пюглепю акнйю
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
     * бнгбпюыюер хлъ тюикю
     * 
     * @return хлъ тюикю
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * бнгбпюыюер йнд пефхлю
     * 
     * @return йнд пефхлю
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * бнгбпюыюер рюилюср
     * 
     * @return рюилюср
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
     * бнгбпюыюер пюглеп акнйю
     * 
     * @return пюглеп акнйю
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
     * бнгбпюыюер пюглеп тюикю
     * 
     * @return пюглеп тюикю
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
     * опнбепъер мю мюкхвхе днонкмхрекэмшу ножхи
     * 
     * @return TRUE еякх днонкмхрекэмше ножхх еярэ, FALSE хмюве
     */
    public boolean hasOptions()
    {
        return (tftpOptions != null);
    }

    /**
     * времхе хлемх хг люяяхбю аюир
     * 
     * @param b люяяхб аюир, яндепфюыхи хлъ тюикю
     * @return хмдейя б люяяхбе оняке гюбепь╗ммнцн мск╗л хлемх тюикю
     * @throws InstantiationException ахрши оюйер
     */
    private int readFileName(
        byte[] b )
        throws InstantiationException
    {
        // опнбепъел мю бшунд гю опедекш люяяхбю
        if ( IX_FILENAME >= b.length )
        {
            throw new InstantiationException( "TFTP request passed to constructor is not a complete "
                                              + getClass().getName() + " packet! It does not contain the filename!" );
        }

        StringBuffer sb = new StringBuffer();

        // вхрюел хлъ тюикю х онксвюел хмдейя ярпнйх пефхлю оепедювх б люяяхбе
        int IX_MODE = StrUtil.readString( IX_FILENAME, b, sb );
        fileName = sb.toString();

        return IX_MODE;
    }

    /**
     * времхе пефхлю оепедювх хг люяяхбю аюир
     * 
     * @param IX_MODE хмдейя пефхлю оепедювх ртро-оюйерю
     * @param b люяяхб аюир оюйерю
     * @return хмдейя люяяхбю оняке нйюмвхбючыеияъ мск╗л ярпнйх пефхлю оепедювх
     * @throws InstantiationException ахрши оюйер
     */
    private int readMode(
        int IX_MODE,
        byte[] b )
        throws InstantiationException
    {
        // опнбепъел мю бшунд гю цпюмхжш люяяхбю
        if ( IX_MODE >= b.length )
        {
            throw new InstantiationException( "TFTP packet passed to constructor is not a complete "
                                              + getClass().getName() + " packet! Mode could not be found" );
        }

        // вхрюел ярпнйс пефхлю
        StringBuffer sb = new StringBuffer();
        int IX_OPTION = StrUtil.readString( IX_MODE, b, sb );
        String modeString = sb.toString();

        // бшвхякъел пефхл оепедювх он явхрюммни ярпнйе
        for ( int i = ASCII_MODE; i <= MAIL_MODE; i++ )
        {
            if ( modeString.compareToIgnoreCase( getModeString( i ) ) == 0 )
            {
                mode = i;
                return IX_OPTION;
            }
        }

        // меонддепфхбюелши пефхл оепедювх
        throw new InstantiationException( "Unsupported mode found in the file request for mode: " + modeString );
    }

    /**
     * сярюмюбкхбюер хлъ тюикю
     * 
     * @param fileName хлъ тюикю
     */
    public void setFileName(
        String fileName )
    {
        this.fileName = fileName;
    }

    /**
     * сярюмюбкхбюер пефхл оепедювх
     * 
     * @param mode пефхл оепедювх
     */
    public void setMode(
        int mode )
    {
        this.mode = mode;
    }

    /**
     * сярюмюбкхбюер рюилюср
     * 
     * @param timeout рюилюср
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
     * сярюмюбкхбюер пюглеп тюикю
     * 
     * @param transferSize пюглеп тюикю
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
     * сярюмюбкхбюер пюглеп акнйю
     * 
     * @param blkSize пюглеп акнйю
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
