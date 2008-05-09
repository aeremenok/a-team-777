package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.edit.FileUploadPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.TraversalPanel;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.widgets.MyTextArea;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class EntityEditPanel
    extends VerticalPanel
{
    protected ImageServiceAsync async       = ImageService.App.getInstance();

    /**
     * таблица правки полей
     */
    protected FlexTable         editTable   = new FlexTable();
    /**
     * панель прокрутки записей
     */
    protected TraversalPanel    traversalPanel;
    /**
     * формуляр с картинкой
     */
    protected EntityForm        entityForm;

    // основные поля
    private MyTextArea          description = new MyTextArea();
    private MyTextBox           name        = new MyTextBox();

    /**
     * счетчик строк в таблице
     */
    private int                 row         = 0;

    public EntityEditPanel()
    {
        super();
        setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

        traversalPanel = createTraversalPanel( this );
        add( traversalPanel );
        add( editTable );

        editTable.setVisible( false );

        // формуляр с картинкой
        createCenteredCell( getEntityForm() );
        // загрузка картинки
        createCenteredCell( new FileUploadPanel( getEntityForm() ) );

        createRow( "Name", name );
        description.setCharacterWidth( 100 );
        description.setVisibleLines( 5 );
        createRow( "Description", description );

        name.addChangeListener( new ChangeListener()
        {
            public void onChange(
                Widget arg0 )
            {
                // подновляем изменившееся имя
                traversalPanel.updateName( name );
            }
        } );
    }

    protected TraversalPanel createTraversalPanel(
        EntityEditPanel entityEditPanel )
    {
        return new TraversalPanel( entityEditPanel );
    }

    public EntityForm getEntityForm()
    {
        if ( entityForm == null )
        {
            entityForm = new EntityForm();
        }
        return entityForm;
    }

    /**
     * @return имя класса сущностей
     */
    public abstract String entityTypeName();

    /**
     * получить все записи с сервера
     * 
     * @param callback обработчик, принимающий записи
     */
    public abstract void retreiveEntities(
        AsyncCallback callback );

    /**
     * настроить формуляр и поля на работу с заданной сущностью
     * 
     * @param entityWrapper сущность
     */
    public void showEntity(
        EntityWrapper entityWrapper )
    {
        name.bindField( entityWrapper.getTitle() );
        description.bindField( entityWrapper.getDescription() );
        getEntityForm().setEntityWrapper( entityWrapper );
    }

    /**
     * вставить в таблицу и центрировать виджет
     * 
     * @param widget что вставить
     */
    protected void createCenteredCell(
        Widget widget )
    {
        row++;
        editTable.setWidget( row, 0, widget );
        editTable.getFlexCellFormatter().setColSpan( row, 0, 2 );
        editTable.getFlexCellFormatter().setHorizontalAlignment( row, 0, HasHorizontalAlignment.ALIGN_CENTER );
    }

    /**
     * создать строку для правки поля
     * 
     * @param name имя поля
     * @param widget виджет для правки поля
     */
    protected void createRow(
        String name,
        Widget widget )
    {
        row++;
        editTable.setWidget( row, 0, new Label( name ) );
        editTable.setWidget( row, 1, widget );
    }

    /**
     * @return путь к стандартной картике
     */
    public abstract String getDefaultImageUrl();

    /**
     * показать таблицу правки (по умолчанию - спрятана, чтоб не мешалась)
     * 
     * @param show показать/спрятать
     */
    public void showEditTable(
        boolean show )
    {
        editTable.setVisible( show );
    }

    public abstract EntityWrapper getMasterpieceReference(
        MasterPiece masterpiece );
}
