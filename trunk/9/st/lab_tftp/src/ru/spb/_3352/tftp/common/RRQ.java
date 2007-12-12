package ru.spb._3352.tftp.common;

/**
 * œ¿ ≈“ «¿œ–Œ—¿ Õ¿ ◊“≈Õ»≈ (OPCODE = 1)
 */
public class RRQ
    extends FRQ
{
    /**
     *  Œƒ Œœ≈–¿÷»» «¿œ–Œ—¿ Õ¿ ◊“≈Õ»≈ ( 1 )
     */
    static public final int OPCODE = 1;

    /**
     *  ŒÕ—“–” “Œ–
     */
    public RRQ()
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
    public RRQ(
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
