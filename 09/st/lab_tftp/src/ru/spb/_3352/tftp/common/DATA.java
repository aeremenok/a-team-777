package ru.spb._3352.tftp.common;

/**
 * оюйер дюммшу (OPCODE = 3)
 */
public class DATA
    extends ACK
{
    /**
     * онкнфемхе дюммшу б люяяхбе аюир
     */
    final static int        IX_DATA = 4;

    /**
     * йнд ноепюжхх оепедювх дюммшу ( 3 )
     */
    static public final int OPCODE  = 3;

    /**
     * люяяхб дюммшу
     */
    byte[]                  data    = {};

    /**
     * йнмярпсйрнп он слнквюмхч
     */
    public DATA()
        throws InstantiationException
    {
        super();
    }

    /**
     * йнмярпсйрнп
     * 
     * @param tftpP люяяхб аюир
     * @param tftpPLength дкхмю люяяхбю аюир
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
     * йнмярпсйрнп
     * 
     * @param blockNr мнлеп акнйю
     * @param data дюммше
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
     * йнмярпсйрнп
     * 
     * @param blockNr мнлеп акнйю
     * @param data люяяхб дюммшу
     * @param offset ялеыемхе б хяундмнл люяяхбе
     * @param len дкхмю дюммшу
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

        // бярюбкъел йнд ноепюжхх
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // бярюбкъел 2-у аюирнбши
        tftpP[IX_BLOCKNR] = (byte) ((blockNr >> 8) & 0xff);
        tftpP[IX_BLOCKNR + 1] = (byte) (blockNr & 0xff);
        // Insert data
        System.arraycopy( data, 0, tftpP, IX_DATA, data.length );
        return tftpP;
    }

    /**
     * бнгбпюыюер люяяхб дюммшу
     * 
     * @return люяяхб дюммшу
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
