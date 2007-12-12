package ru.spb._3352.tftp.common;

/**
 * пеюкхгюжхъ бхпрсюкэмни тюикнбни яхярелш
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
     * хлъ тюикю
     */
    protected String fileName;

    /**
     * пюглеп тюикю
     */
    protected long   fileSize;

    /**
     * рюилюср, нопедекъер бпелъ акнйхпнбюмхъ б лхккхяейсмдюу, опх гмювемхх 0
     * времхе акнйхпсеряъ дн онярсокемхъ дюммшу
     */
    protected int    timeout;

    /**
     * йнмярпсйрнп
     * 
     * @param fileName хлъ тюикю
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
