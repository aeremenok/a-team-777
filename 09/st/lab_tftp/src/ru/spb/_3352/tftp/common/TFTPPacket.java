package ru.spb._3352.tftp.common;

import java.net.InetAddress;

/**
 * юаярюйрмши оюйер ртро
 */
abstract public class TFTPPacket
{
    /**
     * онкнфемхе дюммшу ртро б оюйере
     */
    final static int        IX_OPCODE       = 0;

    /**
     * лхмхлюкэмши пюглеп оюйерю
     */
    static public final int MIN_PACKET_SIZE = 4;

    /**
     * йнд ноепюжхх
     */
    static public final int OPCODE          = 0;

    /**
     * днярю╗р йнд ноепюжхх хг люяяхбю аюирнб, опхьедьху б оюйере
     * 
     * @param packetData дюммше оюйерю
     * @return йнд ноепюжхх
     */
    static public int fetchOpCode(
        byte[] packetData )
    {
        if ( packetData == null )
        {
            return 0;
        }

        if ( packetData.length < IX_OPCODE + 2 )
        {
            return 0;
        }

        return makeword( packetData[IX_OPCODE], packetData[IX_OPCODE + 1] );
    }

    /**
     * янгдюмхе int хг 2-у byte
     * 
     * @param Hi 1-И АЮИР
     * @param Lo 2-И АЮИР
     * @return int
     */
    static public int makeword(
        byte Hi,
        byte Lo )
    {
        return (((Hi << 8) & 0xff00) | (Lo & 0xff));
    }

    /**
     * юдпея мюгмювемхъ
     */
    private InetAddress destAddr;

    /**
     * мнлеп онпрю мюгмювемхъ
     */
    private int         destPort;

    /**
     * йнмярпсйрнп
     */
    public TFTPPacket()
    {
        // йнд ноепюжхх сярюмюбкхбючр онрнлйх
    }

    /**
     * йнмярпсйрнп
     * 
     * @param packetData дюммше оюйерю
     * @throws InstantiationException
     */
    public TFTPPacket(
        byte[] packetData )
        throws InstantiationException
    {
        // хяонкэгнбюрэ getMinPacketSize х getOpcode дкъ оепенопедек╗ммшу
        // ондйкюяяюлх лернднб
        if ( (packetData == null) || (packetData.length < getMinPacketSize()) )
        {
            throw new InstantiationException( "Argument passed to constructor is not a valid " + getClass().getName()
                                              + " packet!" );
        }

        if ( makeword( packetData[IX_OPCODE], packetData[IX_OPCODE + 1] ) != getOpCode() )
        {
            throw new InstantiationException( "Argument passed to constructor is not a valid " + getClass().getName()
                                              + " packet!" );
        }
    }

    /**
     * бнгбпюыюер юдпея
     * 
     * @return юдпея
     */
    public InetAddress getAddress()
    {
        return destAddr;
    }

    /**
     * бнгбпюыюер дюммше оюйерю
     * 
     * @return дюммше оюйерю
     */
    abstract public byte[] getBytes();

    /**
     * бнгбпюыюер лхмхлюкэмши пюглеп оюйерю
     * 
     * @return лхмхлюкэмши пюглеп оюйерю
     */
    public int getMinPacketSize()
    {
        return MIN_PACKET_SIZE;
    }

    /**
     * бнгбпюыюер йнд ноепюжхх
     * 
     * @return йнд ноепюжхх
     */
    abstract public int getOpCode();

    /**
     * бнгбпюыюер мнлеп онпрю
     * 
     * @return мнлеп онпрю
     */
    public int getPort()
    {
        return destPort;
    }

    /**
     * сярюмюбкхбюер юдпея
     * 
     * @param addr юдпея
     */
    public void setAddress(
        InetAddress addr )
    {
        destAddr = addr;
    }

    /**
     * сярюмюбкхбюер онпр
     * 
     * @param port мнлеп онпрю
     */
    public void setPort(
        int port )
    {
        destPort = port;
    }
}
