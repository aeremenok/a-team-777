package ru.spb._3352.tftp.common;

/**
 * ���������� ����������� �������� �������
 */
/**
 * @author ssv
 */
/**
 * @author ssv
 */
public class VirtualFileImpl
    implements
        VirtualFile
{
    /**
     * ��� �����
     */
    protected String fileName;

    /**
     * ������ �����
     */
    protected long   fileSize;

    /**
     * �������, ���������� ����� ������������ � �������������, ��� �������� 0
     * ������ ����������� �� ����������� ������
     */
    protected int    timeout;

    /**
     * �����������
     * 
     * @param fileName ��� �����
     */
    public VirtualFileImpl(
        String fileName )
    {
        this.fileName = fileName;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFile#getFileName()
     */
    public String getFileName()
    {
        return fileName;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFile#getFileSize()
     */
    public long getFileSize()
    {
        return fileSize;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFile#getTimeout()
     */
    public int getTimeout()
    {
        return timeout;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFile#setFileName(java.lang.String)
     */
    public void setFileName(
        String fileName )
    {
        this.fileName = fileName;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFile#setFileSize(long)
     */
    public void setFileSize(
        long fileSize )
    {
        this.fileSize = fileSize;
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFile#setTimeout(int)
     */
    public void setTimeout(
        int timeout )
    {
        this.timeout = timeout;
    }

}
