package ru.spb.hmi.client.docs;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class NotifyCallBack
    implements
        AsyncCallback
{

    private String _string;

    public NotifyCallBack(
        String string )
    {
        _string = string;
    }

    public void onFailure(
        Throwable caught )
    {
        caught.printStackTrace();

    }

    public void onSuccess(
        Object result )
    {
        System.out.println( _string );
    }

}
