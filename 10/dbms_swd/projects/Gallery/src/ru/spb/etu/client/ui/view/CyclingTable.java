package ru.spb.etu.client.ui.view;

import java.util.ArrayList;
import java.util.Iterator;

import ru.spb.etu.client.serializable.EntityWrapper;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * таблица с фиксированной шириной, заполняется зигзагом
 * 
 * @author eav
 */
public class CyclingTable
    extends FlexTable
{
    final int   width;
    private int row = 0;
    private int col = 0;

    public CyclingTable(
        int width )
    {
        super();
        this.width = width;
    }

    public void add(
        Widget widget )
    {
        if ( col == width - 1 )
        {
            row++;
            col = 0;
        }
        setWidget( row, col++, widget );
    }

    /**
     * форма отображения сущности
     * 
     * @author eav
     */
    public class EntityForm
        extends VerticalPanel
    {
        private EntityWrapper entityWrapper;

        public EntityForm(
            EntityWrapper entityWrapper )
        {
            this.entityWrapper = entityWrapper;
            add( new Image( entityWrapper.getImageUrl() ) );
            add( new Label( entityWrapper.getTitle() ) );
        }

        public EntityWrapper getEntityWrapper()
        {
            return entityWrapper;
        }
    }

    /**
     * отобразить сущности в таблице
     * 
     * @param arrayList
     */
    public void fill(
        ArrayList arrayList )
    {
        clear();

        Iterator iterator = arrayList.iterator();
        while ( iterator.hasNext() )
        {
            EntityWrapper entityWrapper = (EntityWrapper) iterator.next();
            add( new EntityForm( entityWrapper ) );
        }
    }

    public EntityWrapper getEntityWrapper(
        int row,
        int col )
    {
        EntityForm entityForm = (EntityForm) getWidget( row, col );
        return entityForm.getEntityWrapper();
    }

    public void clear()
    {
        super.clear();
        row = 0;
        col = 0;
    }
}