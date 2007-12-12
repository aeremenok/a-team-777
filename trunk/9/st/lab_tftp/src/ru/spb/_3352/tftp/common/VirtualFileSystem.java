package ru.spb._3352.tftp.common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * бхпрсюкэмюъ тюикнбюъ яхярелю дкъ яепбепю
 */
public interface VirtualFileSystem
{
    /**
     * онксвхрэ онрнй дкъ времхъ
     * 
     * @param file бхпрсюкэмши тюик
     * @return онрнй времхъ
     * @throws FileNotFoundException тюик ме ясыеярбсер
     */
    public InputStream getInputStream(
        VirtualFile file )
        throws FileNotFoundException;

    /**
     * онксвхрэ онрнй дкъ гюохях
     * 
     * @param file бхпрсюкэмши тюик
     * @return онрнй гюохях
     * @throws FileNotFoundException тюик ме ясыеярбсер
     */
    public OutputStream getOutputStream(
        VirtualFile file )
        throws FileNotFoundException;
}
