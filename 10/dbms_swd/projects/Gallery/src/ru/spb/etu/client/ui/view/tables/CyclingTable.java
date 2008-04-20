package ru.spb.etu.client.ui.view.tables;

import java.util.ArrayList;
import java.util.Iterator;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.ui.view.forms.EntityForm;

import com.google.gwt.user.client.ui.FlexTable;

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
        setWidth( "100%" );
        this.width = width;
    }

    public void addCycled(
        EntityForm widget )
    {
        if ( col == width - 1 )
        {
            row++;
            col = 0;
        }
        setWidget( row, col++, widget );
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
            addCycled( createEntityForm( entityWrapper ) );
        }
    }

    public EntityForm createEntityForm(
        EntityWrapper entityWrapper )
    {
        return new EntityForm( entityWrapper );
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