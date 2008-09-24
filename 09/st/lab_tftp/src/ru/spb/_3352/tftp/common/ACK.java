package ru.spb._3352.tftp.common;

/**
 * ����� ������������� (OPCODE = 6)
 */
public class ACK
    extends TFTPPacket
{
    /**
     * ��������� ������ ����� � ������� ����
     */
    final static int        IX_BLOCKNR = 2;

    /**
     * ��� �������� ������������� ( 4 )
     */
    static public final int OPCODE     = 4;

    /**
     * ����� �����
     */
    protected int           blockNr    = 0;

    /**
     * ����������� �� ��������� (����� ����� 0)
     */
    public ACK()
    {
        this( 0 );
    }

    /**
     * �����������
     * 
     * @param tftpP UDP-�����
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
     * �����������
     * 
     * @param blockNr ����� �����
     */
    public ACK(
        int blockNr )
    {
        super();
        this.blockNr = blockNr;
    }

    /**
     * ���������� ����� �����
     * 
     * @return ����� �����
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

        // ��������� ��� ��������
        tftpP[IX_OPCODE] = (byte) ((OPCODE >> 8) & 0xff);
        tftpP[IX_OPCODE + 1] = (byte) (OPCODE & 0xff);

        // ��������� 2-� �������� ����� �����
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
