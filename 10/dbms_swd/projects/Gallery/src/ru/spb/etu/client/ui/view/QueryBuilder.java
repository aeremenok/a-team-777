package ru.spb.etu.client.ui.view;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.ui.view.tables.CyclingTable;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * ����������� ���������������� ��������
 * 
 * @author eav
 */
public class QueryBuilder
    extends SimplePanel
    implements
        EntityProcessor
{
    /**
     * ������� ��������� �������� ���������
     */
    CyclingTable      cyclingTable  = new CyclingTable( 8 );
    /**
     * ���� ������ ��������
     */
    ChoiceMenu        choiceMenu    = new ChoiceMenu( this );

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
                                            cyclingTable.fill( (ArrayList) arg0 );
                                        }
                                    };

    public QueryBuilder(
        final ViewPanel viewPanel )
    {
        setWidget( choiceMenu );
        cyclingTable.addTableListener( new TableListener()
        {
            public void onCellClicked(
                SourcesTableEvents arg0,
                int arg1,
                int arg2 )
            {
                // ������ ������ ������/�����/����� ������������ ����������
                cyclingTable.getEntityWrapper( arg1, arg2 ).requestMasterPieces();
            }
        } );
    }

    /* (non-Javadoc)
     * @see ru.spb.etu.client.ui.view.EntityProcessor#queryArtists()
     */
    public void processArtists()
    {
        setWidget( cyclingTable );
        async.getArtists( asyncCallback );
    }

    /* (non-Javadoc)
     * @see ru.spb.etu.client.ui.view.EntityProcessor#queryGenres()
     */
    public void processGenres()
    {
        setWidget( cyclingTable );
        async.getGenres( asyncCallback );
    }

    /* (non-Javadoc)
     * @see ru.spb.etu.client.ui.view.EntityProcessor#queryMuseums()
     */
    public void processMuseums()
    {
        setWidget( cyclingTable );
        async.getMuseums( asyncCallback );
    }

    public void reset()
    {
        setWidget( choiceMenu );
    }
}
