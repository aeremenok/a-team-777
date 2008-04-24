package ru.spb.etu.client.serializable;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.ui.view.ResultPanel;
import ru.spb.etu.client.ui.view.ViewPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;

public interface EntityWrapper
    extends
        IsSerializable
{
    ImageServiceAsync async         = ImageService.App.getInstance();

    AsyncCallback     asyncCallback = new AsyncCallback()
                                    {
                                        public void onFailure(
                                            Throwable arg0 )
                                        {
                                            Window.alert( arg0.toString() );
                                        }

                                        public void onSuccess(
                                            Object arg0 )
                                        {
                                            ViewPanel.getInstance()
                                                     .setWidget(
                                                                 ResultPanel.getInstance()
                                                                            .setArtists( (ArrayList) arg0 ) );
                                        }
                                    };

    class ReflectiveString
        implements
            IsSerializable
    {
        public ReflectiveString()
        {
        }

        String string = "";

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
    }

    ReflectiveString getDescription();

    String getImageUrl();

    ReflectiveString getTitle();

    void requestMasterPieces();

    void setImageUrl(
        String results );
}
