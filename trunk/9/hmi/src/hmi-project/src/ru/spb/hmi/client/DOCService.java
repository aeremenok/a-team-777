package ru.spb.hmi.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface DOCService
    extends
        RemoteService
{
    String getContent(
        String id );

    ArrayList getDocList();

    /**
     * Utility class for simplifing access to the instance of async service.
     */
    public static class Util
    {
        private static DOCServiceAsync instance;

        public static DOCServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (DOCServiceAsync) GWT.create( DOCService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                System.out.println( GWT.getModuleBaseURL() + "/DOCService" );
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "/DOCService" );
            }
            return instance;
        }
    }
}
