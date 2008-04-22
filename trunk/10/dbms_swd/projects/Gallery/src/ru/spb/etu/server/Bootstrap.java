package ru.spb.etu.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class Bootstrap
    extends HttpServlet
    implements
        ServletContextListener
{

    private static String path;

    @Override
    public void contextDestroyed(
        ServletContextEvent arg0 )
    {
    }

    @Override
    public void contextInitialized(
        ServletContextEvent arg0 )
    {
        GWT.setUncaughtExceptionHandler( new UncaughtExceptionHandler()
        {
            public void onUncaughtException(
                Throwable arg0 )
            {
                System.err.println( "uncaugt exception" + arg0 );
            }
        } );

        path = arg0.getServletContext().getRealPath( "" );
    }

    @Override
    public void init()
        throws ServletException
    {
    }

    public static String getPath()
    {
        return path;
    }
}
