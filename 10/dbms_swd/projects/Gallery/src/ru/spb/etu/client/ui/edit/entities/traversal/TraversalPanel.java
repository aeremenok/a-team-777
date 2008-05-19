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
    private ArrayList           entities;

    /**
     * панель правки, которой принадлежим
     */
    protected EntityEditPanel   entityEditPanel;

    /**
     * выбор записи для правки
     */
    protected MyListBox         listBox     = new MyListBox();

    protected HorizontalPanel   buttonPanel = new HorizontalPanel();

    protected ImageServiceAsync async       = ImageService.App.getInstance();

    public TraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        this.entityEditPanel = entityEditPanel;
        setHorizontalAlignment( HasHorizontalAlignment.ALIGN_LEFT );

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
        add( buttonPanel );
        entityEditPanel.retreiveEntities( new AsyncCallback()
        {
            public void onFailure(
                Throwable arg0 )
            {
                Window.alert( arg0.toString() );
                remove( buttonPanel );
            }

            public void onSuccess(
                Object arg0 )
            {
                entities = (ArrayList) arg0;
                listBox.clear();
                for ( int i = 0; i < entities.size(); i++ )
                {
                    EntityWrapper entityWrapper = (EntityWrapper) entities.get( i );
                    listBox.addItem( entityWrapper.getTitle().toString() );
                }

                // записи загружены, больше этого не делаем
                listBox.removeClickListener( TraversalPanel.this );
                listBox.addClickListener( new ClickListener()
                {
                    public void onClick(
                        Widget arg0 )
                    {
                        // если запись одна - событие change не происходит
                        if ( listBox.getItemCount() == 1 )
                            onChange( arg0 );
                    }
                } );
                // можно редактировать
                entityEditPanel.showEditTable( true );

                if ( entities.size() > 0 )
                {
                    listBox.setSelectedIndex( 0 );
                    onChange( listBox );
                }
                else
                {
                    addNewEntity();
                }
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
    protected void deleteCurrentEntity()
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
                listBox.setSelectedIndex( listBox.getItemCount() );

                if ( entities.size() == 0 )
                {
                    addNewEntity();
                }
                else
                {
                    entityEditPanel.showEntity( getCurrentEntity() );
                }
            }
        } );
    }

    /**
     * @return текущая запись
     */
    protected EntityWrapper getCurrentEntity()
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
                processNewEntity( (EntityWrapper) arg0 );
            }
        } );
    }

    protected void processNewEntity(
        EntityWrapper entityWrapper )
    {
        entities.add( entityWrapper );
        listBox.addItem( entityWrapper.getTitle().toString() );
        listBox.setSelectedIndex( listBox.getItemCount() - 1 );

        entityWrapper.setImageUrlAndUpdate( entityEditPanel.getDefaultImageUrl() );
        entityEditPanel.showEntity( entityWrapper );
    }
}
