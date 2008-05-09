package ru.spb.etu.client.serializable;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.ui.view.ResultPanel;
import ru.spb.etu.client.ui.view.ViewPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class EntityWrapper
    implements
        IsSerializable
{
    private ReflectiveString       description   = new ReflectiveString( this );

    private ReflectiveString       title         = new ReflectiveString( this );
    protected static AsyncCallback asyncCallback = new AsyncCallback()
                                                 {
                                                     public void onFailure(
                                                         Throwable arg0 )
                                                     {
                                                         Window.alert( arg0.toString() );
                                                     }

                                                     public void onSuccess(
                                                         Object arg0 )
                                                     {
                                                         ViewPanel
                                                                  .getInstance()
                                                                  .setWidget(
                                                                              ResultPanel.getInstance()
                                                                                         .setArtists( (ArrayList) arg0 ) );
                                                     }
                                                 };

    protected String               imageUrl;

    public EntityWrapper()
    {
    }

    public EntityWrapper(
        String description,
        String imageUrl,
        String title )
    {
        this.description.setString( description );
        this.imageUrl = imageUrl;
        this.title.setString( title );
    }

    public ImageServiceAsync getAsync()
    {
        return ImageService.App.getInstance();
    }

    public ReflectiveString getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public ReflectiveString getTitle()
    {
        return title;
    }

    public abstract void requestMasterPieces();

    public void setDescription(
        String description )
    {
        this.description.setString( description );
    }

    public void setImageUrl(
        String url )
    {
        imageUrl = url;
    }

    public void setImageUrlAndUpdate(
        String results )
    {
        imageUrl = results;
        // todo upadate image

        getAsync().updateImage( this, new AsyncCallback()
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

    public void setTitle(
        String title )
    {
        this.title.setString( title );
    }

    public abstract void applyToMasterPiece(
        MasterPiece masterPiece );
}
