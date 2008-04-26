package ru.spb.etu.client.ui.edit.entities.traversal;

import java.util.ArrayList;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.ui.edit.entities.edit.EntityEditPanel;
import ru.spb.etu.client.ui.widgets.MyListBox;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * панель выбора сущностей
 * 
 * @author eav
 */
public abstract class TraversalPanel
    extends HorizontalPanel
    implements
        ChangeListener,
        AsyncCallback,
        ClickListener
{
    private ArrayList       entities;

    private EntityEditPanel entityEditPanel;

    private MyListBox       listBox = new MyListBox();

    ImageServiceAsync       async   = ImageService.App.getInstance();

    public TraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        this.entityEditPanel = entityEditPanel;
        add( createListBox() );

        Button addButton = new Button( "+", new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                addNewEntity();
            }
        } );
        add( addButton );

        Button delButton = new Button( "x", new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                deleteCurrentEntity();
            }
        } );
        add( delButton );
    }

    public void onChange(
        Widget arg0 )
    {
        entityEditPanel.showEntity( getCurrentEntity() );
    }

    private EntityWrapper getCurrentEntity()
    {
        return (EntityWrapper) entities.get( listBox.getSelectedIndex() );
    }

    public void onClick(
        Widget arg0 )
    {
        retreiveEntities( this );
        listBox.removeClickListener( this );
        entityEditPanel.getEditTable().setVisible( true );
    }

    public void onFailure(
        Throwable arg0 )
    {
        Window.alert( arg0.toString() );
    }

    public void onSuccess(
        Object arg0 )
    {
        entities = (ArrayList) arg0;
        listBox.removeItem( 0 );
        for ( int i = 0; i < entities.size(); i++ )
        {
            EntityWrapper entityWrapper = (EntityWrapper) entities.get( i );
            listBox.addItem( entityWrapper.getTitle().toString() );
        }
        listBox.setSelectedIndex( 0 );
        onChange( listBox );
    }

    private ListBox createListBox()
    {
        listBox.addItem( "select item", "" );
        listBox.addClickListener( this );
        listBox.addChangeListener( this );
        return listBox;
    }

    private void deleteCurrentEntity()
    {
        async.remove( getCurrentEntity(), new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                TraversalPanel.this.onFailure( arg0 );
            }

            public void onSuccess(
                Object arg0 )
            {
                entities.remove( listBox.getSelectedIndex() );
                listBox.removeItem( listBox.getSelectedIndex() );
                entityEditPanel.showEntity( getCurrentEntity() );

                if ( entities.size() == 0 )
                {
                    addNewEntity();
                }
            }
        } );
    }

    protected void addNewEntity()
    {
        async.create( entityTypeName(), new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                TraversalPanel.this.onFailure( arg0 );
            }

            public void onSuccess(
                Object arg0 )
            {
                EntityWrapper entityWrapper = (EntityWrapper) arg0;
                entities.add( entityWrapper );
                listBox.addItem( entityWrapper.getTitle().toString() );
                entityEditPanel.showEntity( entityWrapper );
            }
        } );

    }

    protected abstract String entityTypeName();

    protected abstract void retreiveEntities(
        AsyncCallback callback );

    public void updateName(
        MyTextBox name )
    {
        listBox.setItemText( listBox.getSelectedIndex(), name.getText() );
    }

}
