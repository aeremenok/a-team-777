package ru.spb.etu.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import ru.spb.etu.server.util.EntityBackuperImpl;

public class FileUploadServlet
    extends HttpServlet
{
    protected static Logger log = Logger.getLogger( EntityBackuperImpl.class );
    static
    {
        log.setLevel( org.apache.log4j.Level.ALL );
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response )
        throws ServletException,
            IOException
    {
        response.setContentType( "text/plain" );
        getFileItem( request, response );
    }

    private void getFileItem(
        HttpServletRequest request,
        HttpServletResponse response )
    {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload( factory );

        try
        {
            List items = upload.parseRequest( request );
            Iterator it = items.iterator();
            while ( it.hasNext() )
            {
                FileItem item = (FileItem) it.next();
                if ( !item.isFormField() && "testcaseFile".equals( item.getFieldName() ) )
                {
                    response.getWriter().write( saveToFile( item ) );
                    break;
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private String saveToFile(
        FileItem item )
        throws FileNotFoundException,
            IOException
    {
        // полное имя загруженного файла
        String path = item.getName();
        // полное имя сохраненного файла
        String name = "images/" + path.substring( path.lastIndexOf( "\\" ) + 1 );
        String fullName = getModulePath() + "/" + name;

        FileOutputStream fileOutputStream = new FileOutputStream( new File( fullName ) );
        fileOutputStream.write( item.get() );
        fileOutputStream.flush();

        return name;
    }

    /**
     * @return путь к папке, где храним
     */
    private static String getModulePath()
    {
        return Bootstrap.getPath() + "/" + ImageServiceImpl.getBaseUrl();
    }

    /**
     * сохранить файл на диск
     * 
     * @param imageUrl
     * @param bytes
     * @throws IOException
     */
    public static void writeBytes(
        String imageUrl,
        byte[] bytes )
        throws IOException
    {
        if ( imageUrl == null )
        {
            log.warn( " can not writeBytes: imageUrl is null" );
            return;
        }
        if ( bytes == null || bytes.length == 0 )
        {
            log.warn( " can not writeBytes: no bytes" );
            return;
        }
        File file = new File( getModulePath() + "/" + imageUrl );
        log.info( " Writing file " + file.getCanonicalPath() + " (" + bytes.length + " bytes)" );
        new FileOutputStream( file ).write( bytes );
    }

    /**
     * загрузить файл с диска
     * 
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(
        String imageUrl )
        throws IOException
    {
        if ( imageUrl == null )
        {
            log.warn( " can not readBytes: imageUrl is null" );
            return null;
        }
        File file = new File( getModulePath() + "/" + imageUrl );
        log.info( "Reading file " + file.getCanonicalPath() + " ( " + file.length() + " bytes )" );

        byte[] fileContent = new byte[(int) file.length()];
        new FileInputStream( file ).read( fileContent );
        return fileContent;
    }
}
