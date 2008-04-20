package ru.spb.etu.client.ui.view;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * панель просмотра
 * 
 * @author eav
 */
public class ViewPanel
    extends SimplePanel
{
    static ViewPanel instance = null;

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

    public static ViewPanel reset()
    {
        getInstance().queryBuilder.reset();
        getInstance().setWidget( getInstance().queryBuilder );
        return getInstance();
    }
}
