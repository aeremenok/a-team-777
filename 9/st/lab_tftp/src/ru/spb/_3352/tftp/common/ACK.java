package ru.spb._3352.tftp.common;

/**
 * ÏÀÊÅÒ ÏÎÄÒÂÅÐÆÄÅÍÈß (OPCODE = 6)
 */
public class ACK
    extends TFTPPacket
{
    /**
     * ÏÎËÎÆÅÍÈÅ ÍÎÌÅÐÀ ÁËÎÊÀ Â ÌÀÑÑÈÂÅ ÁÀÉÒ
     */
    final static int        IX_BLOCKNR = 2;

    /**
     * ÊÎÄ ÎÏÅÐÀÖÈÈ ÏÎÄÒÂÅÐÆÄÅÍÈß ( 4 )
     */
    static public final int OPCODE     = 4;

    /**
     * ÍÎÌÅÐ ÁËÎÊÀ
     */
    protected int           blockNr    = 0;

    /**
     * ÊÎÍÑÒÐÓÊÒÎÐ ÏÎ ÓÌÎË×ÀÍÈÞ (ÍÎÌÅÐ ÁËÎÊÀ 0)
     */
    public ACK()
    {
        this( 0 );
    }

    /**
     * ÊÎÍÑÒÐÓÊÒÎÐ
     * 
     * @param tftpP UDP-ÏÀÊÅÒ
     * @throws InstantiationException
     */
    public ACK(
        byte[] tftpP )
        throws InstantiationException
    {
        super( tftpP );
        blockNr = makeword( tftpP[IX_BLOCKNR], tftpP[IX_BLOCKNR + 1] );
    }

    /**
     * ÊÎÍÑÒÐÓÊÒÎÐ
     * 
     * @param blockNr ÍÎÌÅÐ ÁËÎÊÀ
     */
    public ACK(
        int blockNr )
    {
        super();
        this.blockNr = blockNr;
    }

    /**
     * ÂÎÇÂÐÀÙÀÅÒ ÍÎÌÅÐ ÁËÎÊÀ
     * 
     * @return ÍÎÌÅÐ ÁËÎÊÀ
     */
    public int getBlockNr()
    {
        return blockNr;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.TFTPPacket#getBytes()
     */
    public byte[] getBytes()
    {
        byte[] tftpP = new byte[MIN_PACKET_SIZE];

        // ÂÑÒÀÂËßÅÌ ÊÎÄ ÎÏÅÐÀÖÈÈ
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // ÂÑÒÀÂËßÅÌ 2-Õ ÁÀÉÒÎÂÛÉ ÍÎÌÅÐ ÁËÎÊÀ
        tftpP[IX_BLOCKNR] = (byte) ((blockNr >> 8) & 0xff);
        tftpP[IX_BLOCKNR + 1] = (byte) (blockNr & 0xff);

        return tftpP;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.TFTPPacket#getOpCode()
     */
    public int getOpCode()
    {
        return OPCODE;
    }
}
