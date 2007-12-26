package ru.spb.hmi.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DOCServiceAsync
{
    void getContent(
        String id, AsyncCallback callback );

    void getDocList(AsyncCallback callback);
}
