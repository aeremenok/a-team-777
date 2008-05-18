package ru.spb.etu.client.ui.view;

import java.util.ArrayList;
import java.util.Iterator;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.ui.view.tables.CyclingTable;
import ru.spb.etu.client.ui.view.tables.MasterPieceTable;
import ru.spb.etu.client.ui.widgets.MyListBox;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * панель отображения результатов запроса
 * 
 * @author eav
 */
public class ResultPanel
    extends VerticalPanel
    implements
        TabListener,
        ChangeListener
{
    private static final String PAINTINGS  = "Paintings";

    private static final String SCULPTURES = "Sculptures";

    private static final String ALL        = "All";

    ArrayList                   artists;

    ImageServiceAsync           async      = ImageService.App.getInstance();

    MyListBox                   listBox    = new MyListBox();

    TabPanel                    tabPanel   = new TabPanel();

    private int                 lastSelectedTabIndex;

    private ResultPanel()
    {
        setHorizontalAlignment( ALIGN_CENTER );

        listBox.addItem( ALL );
        listBox.addItem( SCULPTURES );
        listBox.addItem( PAINTINGS );
        listBox.addChangeListener( this );

        add( listBox );
        add( tabPanel );

        setWidth( "100%" );
        tabPanel.addStyleName( "gwt-TabBar" );
        tabPanel.addTabListener( this );
    }

    static ResultPanel instance;

    private class MasterpieceArrangingCallBack
        implements
            AsyncCallback
    {
        private Artist artist;

        public MasterpieceArrangingCallBack(
            Artist artist )
        {
            super();
            this.artist = artist;
        }

        public void onFailure(
            Throwable arg0 )
        {
            Window.alert( arg0.toString() );
        }

        public void onSuccess(
            Object arg0 )
        {
            int index = artists.indexOf( artist );
            DeckPanel deckPanel = tabPanel.getDeckPanel();

            deckPanel.showWidget( index );
            CyclingTable cyclingTable = (CyclingTable) deckPanel.getWidget( index );
            cyclingTable.fill( (ArrayList) arg0 );
        }
    }

    public static ResultPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new ResultPanel();
        }
        return instance;
    }

    public boolean onBeforeTabSelected(
        SourcesTabEvents arg0,
        int arg1 )
    {
        return false;
    }

    public void onChange(
        Widget arg0 )
    {
        onTabSelected( tabPanel, lastSelectedTabIndex );
    }

    public void onTabSelected(
        SourcesTabEvents arg0,
        int tabIndex )
    {
        this.lastSelectedTabIndex = tabIndex;
        // получаем с сервера произведения выбранного художника
        Artist artist = getSelectedArtist();

        MasterpieceArrangingCallBack masterpieceArrangingCallBack = new MasterpieceArrangingCallBack( artist );
        if ( listBox.getText().equals( ALL ) )
        {
            async.getMasterPieces( artist, masterpieceArrangingCallBack );
        }
        else if ( listBox.getText().equals( SCULPTURES ) )
        {
            async.getSculptures( artist, masterpieceArrangingCallBack );
        }
        else if ( listBox.getText().equals( PAINTINGS ) )
        {
            async.getPaintings( artist, masterpieceArrangingCallBack );
        }
    }

    private Artist getSelectedArtist()
    {
        return (Artist) artists.get( lastSelectedTabIndex );
    }

    public ResultPanel setArtists(
        final ArrayList artists )
    {
        tabPanel.clear();

        this.artists = artists;
        if ( !artists.isEmpty() )
        {
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
                        tabPanel.onTabSelected( tabPanel, artists.indexOf( artist ) );
                    }
                } );
                tabPanel.add( new MasterPieceTable( 8 ), label );
            }

            tabPanel.selectTab( 0 );
            onTabSelected( tabPanel, 0 );
        }
        return this;
    }
}
