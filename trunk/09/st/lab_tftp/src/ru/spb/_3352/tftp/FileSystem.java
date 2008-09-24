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
 * �������� ������� ��� �������
 */
public class FileSystem
    implements
        VirtualFileSystem
{
    /**
     * �������� ���������� ��� ������
     */
    private File home;

    /**
     * �����������
     * 
     * @param home �������� ����������
     */
    public FileSystem(
        String home )
    {
        this.home = new File( home );
    }

    /**
     * ������������ ������ � ������ ����� � ������ �� �����, ��� �������
     * ���������� ����, ��������� .., ������������ ����������
     * 
     * @param ��� �����
     * @return ���� � ������ ������
     * @throws FileNotFoundException ������ ������������ ..
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
