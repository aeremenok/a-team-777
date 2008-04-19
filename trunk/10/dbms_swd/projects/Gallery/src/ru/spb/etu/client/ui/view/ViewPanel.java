package ru.spb.etu.client.ui.view;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.Artist;
import ru.spb.etu.client.serializable.EntityWrapper;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * панель просмотра
 * 
 * @author eav
 */
public class ViewPanel
    extends SimplePanel
{
    static ViewPanel  instance = null;
    ImageServiceAsync async    = ImageService.App.getInstance();

    public static ViewPanel getInstance()
    {
        if ( instance == null )
        {
            instance = new ViewPanel();
        }
        return instance;
    }

    public ViewPanel()
    {
        setWidget( queryBuilder );
    }

    QueryBuilder queryBuilder = new QueryBuilder( this );

    public void addCriterion(
        EntityWrapper entityWrapper )
    {
        // TODO заглушка
        if ( entityWrapper instanceof Artist )
        {
            ArrayList artists = new ArrayList();
            artists.add( entityWrapper );
            setWidget( new ResultPanel( artists ) );
        }
    }
}
