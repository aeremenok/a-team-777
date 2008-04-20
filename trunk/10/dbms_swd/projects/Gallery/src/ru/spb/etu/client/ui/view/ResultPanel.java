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
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * панель отображения результатов запроса
 * 
 * @author eav
 */
public class ResultPanel
    extends TabPanel
{
    ImageServiceAsync async        = ImageService.App.getInstance();
    CyclingTable      cyclingTable = new MasterPieceTable( 7 );

    public ResultPanel(
        final ArrayList artists )
    {
        setWidth( "100%" );
        addStyleName( "gwt-TabBar" );

        // группируем художников по вкладкам
        Iterator iterator = artists.iterator();
        while ( iterator.hasNext() )
        {
            Artist artist = (Artist) iterator.next();
            this.add( cyclingTable, artist.getName() );
        }

        TabListener tabListener = new TabListener()
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

        selectTab( 0 );
        addTabListener( tabListener );
        tabListener.onTabSelected( this, 0 );
    }

    private void getMasterPieces(
        Artist artist )
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
                cyclingTable.fill( (ArrayList) arg0 );
            }
        } );
    }
}
