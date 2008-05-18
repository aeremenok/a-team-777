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
    private ReflectiveString description   = new ReflectiveString( this );

    private ReflectiveString title         = new ReflectiveString( this );
    static AsyncCallback     asyncCallback = new AsyncCallback()
                                           {
                                               public void onFailure(
                                                   Throwable arg0 )
                                               {
                                                   Window.alert( arg0.toString() );
                                               }

                                               public void onSuccess(
                                                   Object arg0 )
                                               {
                                                   ResultPanel instance = ResultPanel.getInstance();
                                                   ResultPanel resultPanel = instance.setArtists( (ArrayList) arg0 );
                                                   ViewPanel.getInstance().setWidget( resultPanel );
                                               }
                                           };

    protected String         imageUrl;
    protected Integer        id;

    public EntityWrapper()
    {
        imageUrl = "";
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
                setId( (Integer) arg0 );
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

    public Integer getId()
    {
        return id;
    }

    public void setId(
        Integer id )
    {
        this.id = id;
    }

}
