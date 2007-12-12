package ru.spb._3352.tftp.common;

import java.net.InetAddress;

/**
 * ���������� ����� ����
 */
abstract public class TFTPPacket
{
    /**
     * ��������� ������ ���� � ������
     */
    final static int        IX_OPCODE       = 0;

    /**
     * ����������� ������ ������
     */
    static public final int MIN_PACKET_SIZE = 4;

    /**
     * ��� ��������
     */
    static public final int OPCODE          = 0;

    /**
     * ������� ��� �������� �� ������� ������, ��������� � ������
     * 
     * @param packetData ������ ������
     * @return ��� ��������
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
     * �������� int �� 2-� byte
     * 
     * @param Hi 1-� ����
     * @param Lo 2-� ����
     * @return int
     */
    static public int makeword(
        byte Hi,
        byte Lo )
    {
        return (((Hi << 8) & 0xff00) | (Lo & 0xff));
    }

    /**
     * ����� ����������
     */
    private InetAddress destAddr;

    /**
     * ����� ����� ����������
     */
    private int         destPort;

    /**
     * �����������
     */
    public TFTPPacket()
    {
        // ��� �������� ������������� �������
    }

    /**
     * �����������
     * 
     * @param packetData ������ ������
     * @throws InstantiationException
     */
    public TFTPPacket(
        byte[] packetData )
        throws InstantiationException
    {
        // ������������ getMinPacketSize � getOpcode ��� ����������˨����
        // ����������� �������
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
     * ���������� �����
     * 
     * @return �����
     */
    public InetAddress getAddress()
    {
        return destAddr;
    }

    /**
     * ���������� ������ ������
     * 
     * @return ������ ������
     */
    abstract public byte[] getBytes();

    /**
     * ���������� ����������� ������ ������
     * 
     * @return ����������� ������ ������
     */
    public int getMinPacketSize()
    {
        return MIN_PACKET_SIZE;
    }

    /**
     * ���������� ��� ��������
     * 
     * @return ��� ��������
     */
    abstract public int getOpCode();

    /**
     * ���������� ����� �����
     * 
     * @return ����� �����
     */
    public int getPort()
    {
        return destPort;
    }

    /**
     * ������������� �����
     * 
     * @param addr �����
     */
    public void setAddress(
        InetAddress addr )
    {
        destAddr = addr;
    }

    /**
     * ������������� ����
     * 
     * @param port ����� �����
     */
    public void setPort(
        int port )
    {
        destPort = port;
    }
}
