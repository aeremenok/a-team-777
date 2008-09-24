package ru.spb.hmi.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DOCServiceAsync
{
    void getDocContent(
        String id, AsyncCallback callback );

    void getDocList(AsyncCallback callback);

    void prepareDoc(
        String id, AsyncCallback callback );

    void getProperty(
        String path, AsyncCallback callback );

    void setProperty(
        String parentType,
        String text, AsyncCallback callback );
}
