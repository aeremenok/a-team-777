package ru.spb.etu.client.serializable;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ReflectiveString
    implements
        IsSerializable
{
    EntityWrapper host;

    String        string = "";

    public ReflectiveString()
    {
    }

    public ReflectiveString(
        EntityWrapper host )
    {
        super();
        this.host = host;
    }

    public ReflectiveString(
        String string )
    {
        super();
        this.string = string;
    }

    public void setString(
        String string )
    {
        this.string = string;
    }

    public String toString()
    {
        return string;
    }

    public void updateHost()
    {
        ImageServiceAsync async = ImageService.App.getInstance();
        async.saveOrUpdate( host, new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                Window.alert( arg0.toString() );
            }

            public void onSuccess(
                Object arg0 )
            {
            }
        } );
    }

    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (string == null ? 0 : string.hashCode());
        return result;
    }

    public boolean equals(
        Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !(obj instanceof ReflectiveString) )
            return false;
        final ReflectiveString other = (ReflectiveString) obj;
        if ( string == null )
        {
            if ( other.string != null )
                return false;
        }
        else if ( !string.equals( other.string ) )
            return false;
        return true;
    }

}
