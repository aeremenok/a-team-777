package ru.spb.etu.client.ui.view;

import java.util.ArrayList;
import java.util.Iterator;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.ui.view.tables.CyclingTable;
import ru.spb.etu.client.ui.view.tables.MasterPieceTable;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * панель отображения результатов запроса
 * 
 * @author eav
 */
public class ResultPanel
    extends TabPanel
{
    ArrayList         artists;

    ImageServiceAsync async       = ImageService.App.getInstance();

    TabListener       tabListener = new TabListener()
                                  {
                                      public boolean onBeforeTabSelected(
                                          SourcesTabEvents arg0,
                                          int arg1 )
                                      {
                                          return false;
                                      }

                                      public void onTabSelected(
                                          SourcesTabEvents arg0,
                                          int tabIndex )
                                      {
                                          // получаем с сервера произведения выбранного художника
                                          getMasterPieces( (Artist) artists.get( tabIndex ) );
                                      }
                                  };

    public ResultPanel()
    {
        setWidth( "100%" );
        addStyleName( "gwt-TabBar" );
        addTabListener( tabListener );
    }

    static ResultPanel instance;

    public static ResultPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new ResultPanel();
        }
        return instance;
    }

    public ResultPanel setArtists(
        final ArrayList artists )
    {
        clear();

        this.artists = artists;
        // группируем художников по вкладкам
        Iterator iterator = artists.iterator();
        while ( iterator.hasNext() )
        {
            final Artist artist = (Artist) iterator.next();
            // в TabPanel в каждой вкладке д.б. свой виджет
            Label label = new Label( artist.getName().toString() );
            label.addClickListener( new ClickListener()
            {
                public void onClick(
                    Widget arg0 )
                {
                    onTabSelected( ResultPanel.this, artists.indexOf( artist ) );
                }
            } );
            add( new MasterPieceTable( 8 ), label );
        }

        selectTab( 0 );
        tabListener.onTabSelected( this, 0 );
        return this;
    }

    private void getMasterPieces(
        final Artist artist )
    {
        async.getMasterPieces( artist, new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                Window.alert( arg0.toString() );
            }

            public void onSuccess(
                Object arg0 )
            {
                int index = artists.indexOf( artist );
                getDeckPanel().showWidget( index );
                CyclingTable cyclingTable = (CyclingTable) getDeckPanel().getWidget( index );
                cyclingTable.fill( (ArrayList) arg0 );
            }
        } );
    }
}
