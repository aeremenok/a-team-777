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
    String getTitle();

    String getImageUrl();

    String getDescription();

    void requestMasterPieces();

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
}
