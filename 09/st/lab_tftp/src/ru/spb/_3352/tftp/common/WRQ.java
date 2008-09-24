package ru.spb._3352.tftp.common;

/**
 * œ¿ ≈“ «¿œ–Œ—¿ Õ¿ «¿œ»—‹ (OPCODE = 2)
 */
public class WRQ
    extends FRQ
{
    /**
     *  Œƒ «¿œ–Œ—¿ Õ¿ «¿œ»—‹ ( 2 )
     */
    static public final int OPCODE = 2;

    /**
     *  ŒÕ—“–” “Œ–
     * 
     * @throws InstantiationException
     */
    public WRQ()
        throws InstantiationException
    {
        super();
    }

    /**
     *  ŒÕ—“–” “Œ–
     * 
     * @param tftpP UDP-œ¿ ≈“
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
