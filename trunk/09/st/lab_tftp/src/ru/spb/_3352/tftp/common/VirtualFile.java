package ru.spb._3352.tftp.common;

/**
 * ����������� ����
 */
public interface VirtualFile
{
    /**
     * ���������� ��� �����
     * 
     * @return ��� �����
     */
    public String getFileName();

    /**
     * ���������� ������ �����
     * 
     * @return ������ �����
     */
    public long getFileSize();

    /**
     * ���������� ����� ��������
     * 
     * @return ����� ��������
     */
    public int getTimeout();

    /**
     * ������������� ��� �����
     * 
     * @param fileName ����� ��� �����
     */
    public void setFileName(
        String fileName );

    /**
     * ������������� ������ �����
     * 
     * @param fileSize ����� ������ �����
     */
    public void setFileSize(
        long fileSize );

    /**
     * ������������� ����� ��������
     * 
     * @param timeout ����� ����� ��������
     */
    public void setTimeout(
        int timeout );

}
