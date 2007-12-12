package ru.spb._3352.tftp.common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ����������� �������� ������� ��� �������
 */
public interface VirtualFileSystem
{
    /**
     * �������� ����� ��� ������
     * 
     * @param file ����������� ����
     * @return ����� ������
     * @throws FileNotFoundException ���� �� ����������
     */
    public InputStream getInputStream(
        VirtualFile file )
        throws FileNotFoundException;

    /**
     * �������� ����� ��� ������
     * 
     * @param file ����������� ����
     * @return ����� ������
     * @throws FileNotFoundException ���� �� ����������
     */
    public OutputStream getOutputStream(
        VirtualFile file )
        throws FileNotFoundException;
}
