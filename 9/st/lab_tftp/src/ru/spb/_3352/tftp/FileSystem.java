package ru.spb._3352.tftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import ru.spb._3352.tftp.common.VirtualFile;
import ru.spb._3352.tftp.common.VirtualFileSystem;

/**
 * ÔÀÉËÎÂÀß ÑÈÑÒÅÌÀ ÄËß ÑÅÐÂÅÐÀ
 */
public class FileSystem
    implements
        VirtualFileSystem
{
    /**
     * ÄÎÌÀØÍßß ÄÈÐÅÊÒÎÐÈß ÄËß ÔÀÉËÎÂ
     */
    private File home;

    /**
     * ÊÎÍÑÒÐÓÊÒÎÐ
     * 
     * @param home ÄÎÌÀØÍßß ÄÈÐÅÊÒÎÐÈß
     */
    public FileSystem(
        String home )
    {
        this.home = new File( home );
    }

    /**
     * ÑÎÏÎÑÒÀÂËßÅÒ ÑÒÐÎÊÓ Ñ ÈÌÅÍÅÌ ÔÀÉËÀ Ñ ÔÀÉËÎÌ ÍÀ ÄÈÑÊÅ, ÏÐÈ ÏÎÏÛÒÊÅ
     * ÎÁÐÀÒÈÒÜÑß ÂÛØÅ, ÈÑÏÎËÜÇÓß .., ÃÅÍÅÐÈÐÓÅÒÑß ÈÑÊËÞ×ÅÍÈÅ
     * 
     * @param ÈÌß ÔÀÉËÀ
     * @return ÔÀÉË Ñ ÄÀÍÍÛÌ ÈÌÅÍÅÌ
     * @throws FileNotFoundException ÍÅËÜÇß ÏÎËÜÇÎÂÀÒÜÑß ..
     */
    public File expand(
        String name )
        throws FileNotFoundException
    {
        if ( name.indexOf( ".." ) > -1 )
        {
            throw new FileNotFoundException( "No tricks with .. allowed" );
        }

        return new File( home, name );
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFileSystem#getInputStream(ru.spb._3352.tftp.common.VirtualFile)
     */
    public InputStream getInputStream(
        VirtualFile file )
        throws FileNotFoundException
    {
        return new FileInputStream( expand( file.getFileName() ) );
    }

    /* (non-Javadoc)
     * @see ru.spb._3352.tftp.common.VirtualFileSystem#getOutputStream(ru.spb._3352.tftp.common.VirtualFile)
     */
    public OutputStream getOutputStream(
        VirtualFile file )
        throws FileNotFoundException
    {
        return new FileOutputStream( expand( file.getFileName() ) );
    }

}
