package ru.spb._3352.tftp.common;

/**
 * бхпрсюкэмши тюик
 */
public interface VirtualFile
{
    /**
     * бнгбпюыюер хлъ тюикю
     * 
     * @return хлъ тюикю
     */
    public String getFileName();

    /**
     * бнгбпюыюер пюглеп тюикю
     * 
     * @return пюглеп тюикю
     */
    public long getFileSize();

    /**
     * бнгбпюыюер бпелъ нфхдюмхъ
     * 
     * @return бпелъ нфхдюмхъ
     */
    public int getTimeout();

    /**
     * сярюмюбкхбюер хлъ тюикю
     * 
     * @param fileName мнбне хлъ тюикю
     */
    public void setFileName(
        String fileName );

    /**
     * сярюмюбкхбюер пюглеп тюикю
     * 
     * @param fileSize мнбши пюглеп тюикю
     */
    public void setFileSize(
        long fileSize );

    /**
     * сярюмюбкхбюер бпелъ нфхдюмхъ
     * 
     * @param timeout мнбне бпелъ нфхдюмхъ
     */
    public void setTimeout(
        int timeout );

}
