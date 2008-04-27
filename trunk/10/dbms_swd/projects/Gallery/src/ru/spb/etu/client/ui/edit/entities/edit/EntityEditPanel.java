package ru.spb.etu.client.ui.edit.entities.edit;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.ui.edit.FileUploadPanel;
import ru.spb.etu.client.ui.edit.entities.traversal.TraversalPanel;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.widgets.HasValue;
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
    ImageServiceAsync  async          = ImageService.App.getInstance();

    /**
     * таблица правки полей
     */
    FlexTable          editTable      = new FlexTable();
    /**
     * панель прокрутки записей
     */
    TraversalPanel     traversalPanel = new TraversalPanel( this );
    /**
     * формуляр с картинкой
     */
    EntityForm         entityForm     = new EntityForm();

    // основные поля
    private MyTextArea description    = new MyTextArea();
    private MyTextBox  name           = new MyTextBox();

    public EntityEditPanel()
    {
        super();
        setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

        add( traversalPanel );
        add( editTable );

        editTable.setVisible( false );

        int row = 0;
        // формуляр с картинкой
        createCenteredCell( row++, entityForm );
        // загрузка картинки
        createCenteredCell( row++, new FileUploadPanel( entityForm ) );

        createRow( row++, "Name", name );
        createRow( row++, "Description", description );

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

    public EntityForm getEntityForm()
    {
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
        entityForm.setEntityWrapper( entityWrapper );
    }

    /**
     * вставить в таблицу и центрировать виджет
     * 
     * @param row куда вставить
     * @param widget что вставить
     */
    private void createCenteredCell(
        int row,
        Widget widget )
    {
        editTable.setWidget( row, 0, widget );
        editTable.getFlexCellFormatter().setColSpan( row, 0, 2 );
        editTable.getFlexCellFormatter().setHorizontalAlignment( row, 0, HasHorizontalAlignment.ALIGN_CENTER );
    }

    /**
     * создать строку для правки поля
     * 
     * @param row где создать
     * @param name имя поля
     * @param hasValue виджет для правки поля
     */
    private void createRow(
        int row,
        String name,
        HasValue hasValue )
    {
        editTable.setWidget( row, 0, new Label( name ) );
        editTable.setWidget( row, 1, (Widget) hasValue );
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
}
