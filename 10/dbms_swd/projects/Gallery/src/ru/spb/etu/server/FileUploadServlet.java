package ru.spb.etu.server;

import java.io.File;
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

public class FileUploadServlet
    extends HttpServlet
{
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
        // путь к папке, где все храним
        String modulePath = Bootstrap.getPath() + "/" + ImageServiceImpl.getBaseUrl();
        // полное имя сохраненного файла
        String name = "images/" + path.substring( path.lastIndexOf( "\\" ) + 1 );
        String fullName = modulePath + "/" + name;

        FileOutputStream fileOutputStream = new FileOutputStream( new File( fullName ) );
        fileOutputStream.write( item.get() );
        fileOutputStream.flush();

        return name;
    }
}
