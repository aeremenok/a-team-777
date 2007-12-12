package ru.spb._3352.tftp.server;

import java.net.InetAddress;

/**
 * ��������� ������� ���������� � �������
 */
public interface EventListener
    extends
        java.util.EventListener
{
    /**
     * ����� ���������� � ���������� �����
     * 
     * @param addr ����� �������
     * @param port ����
     * @param fileName ��� �����
     * @param ok ���������� ������ �������?
     */
    public void onAfterDownload(
        InetAddress addr,
        int port,
        String fileName,
        boolean ok );

    /**
     * ����� ���������� � ������� �����
     * 
     * @param addr ����� �������
     * @param port ����
     * @param fileName ��� �����
     * @param ok ������� ������ �������?
     */
    public void onAfterUpload(
        InetAddress addr,
        int port,
        String fileName,
        boolean ok );
}
