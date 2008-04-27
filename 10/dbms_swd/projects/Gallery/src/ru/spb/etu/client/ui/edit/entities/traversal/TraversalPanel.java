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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * панель выбора сущностей
 * 
 * @author eav
 */
public class TraversalPanel
    extends HorizontalPanel
    implements
        ChangeListener,
        ClickListener
{
    /**
     * все доступные для выбора записи
     */
    private ArrayList       entities;

    /**
     * панель правки, которой принадлежим
     */
    private EntityEditPanel entityEditPanel;

    /**
     * выбор записи для правки
     */
    private MyListBox       listBox     = new MyListBox();

    HorizontalPanel         buttonPanel = new HorizontalPanel();

    ImageServiceAsync       async       = ImageService.App.getInstance();

    public TraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        this.entityEditPanel = entityEditPanel;
        setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

        add( listBox );
        listBox.addItem( "select item", "" );
        listBox.setWidth( "300" );
        listBox.addClickListener( this );
        listBox.addChangeListener( this );

        Button addButton = new Button( "+", new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                addNewEntity();
            }
        } );

        buttonPanel.add( addButton );

        Button delButton = new Button( "x", new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                deleteCurrentEntity();
            }
        } );
        buttonPanel.add( delButton );
    }

    public void onChange(
        Widget arg0 )
    {
        entityEditPanel.showEntity( getCurrentEntity() );
    }

    public void onClick(
        Widget arg0 )
    {
        entityEditPanel.retreiveEntities( new AsyncCallback()
        {
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

                // записи загружены, больше этого не делаем
                listBox.removeClickListener( TraversalPanel.this );
                // можно редактировать
                entityEditPanel.showEditTable( true );
                add( buttonPanel );

                onChange( listBox );
            }
        } );

    }

    /**
     * подновить изменившееся имя
     * 
     * @param name имя
     */
    public void updateName(
        MyTextBox name )
    {
        listBox.setItemText( listBox.getSelectedIndex(), name.getText() );
        entityEditPanel.getEntityForm().setEntityWrapper( getCurrentEntity() );
    }

    /**
     * удалить текущую запись
     */
    private void deleteCurrentEntity()
    {
        async.remove( getCurrentEntity(), new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                Window.alert( arg0.toString() );
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

    /**
     * @return текущая запись
     */
    private EntityWrapper getCurrentEntity()
    {
        return (EntityWrapper) entities.get( listBox.getSelectedIndex() );
    }

    /**
     * добавить новую запись
     */
    protected void addNewEntity()
    {
        async.create( entityEditPanel.entityTypeName(), new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                Window.alert( arg0.toString() );
            }

            public void onSuccess(
                Object arg0 )
            {
                EntityWrapper entityWrapper = (EntityWrapper) arg0;
                entities.add( entityWrapper );
                listBox.addItem( entityWrapper.getTitle().toString() );
                listBox.setSelectedIndex( listBox.getItemCount() - 1 );

                entityWrapper.setImageUrl( entityEditPanel.getDefaultImageUrl() );
                entityEditPanel.showEntity( entityWrapper );
            }
        } );

    }
}
