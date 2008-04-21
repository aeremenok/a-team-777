package ru.spb.etu.server;

import java.io.File;
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
import org.apache.commons.fileupload.FileUploadException;
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

        FileItem uploadItem = getFileItem( request );
        if ( uploadItem == null )
        {
            response.getWriter().write( "NO-SCRIPT-DATA" );
            return;
        }

        response.getWriter().write( new String( uploadItem.get() ) );
    }

    private FileItem getFileItem(
        HttpServletRequest request )
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
                    File file = new File( item.getName() + "1" );
                    FileOutputStream fileOutputStream = new FileOutputStream( file );
                    fileOutputStream.write( item.get() );
                    fileOutputStream.flush();
                    return item;
                }
            }
        }
        catch ( FileUploadException e )
        {
            return null;
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }
}
