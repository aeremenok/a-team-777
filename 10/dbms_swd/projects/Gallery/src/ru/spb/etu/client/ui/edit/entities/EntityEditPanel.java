package ru.spb.etu.client.ui.edit.entities;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.EntityWrapper.ReflectiveString;
import ru.spb.etu.client.ui.edit.FileUploadPanel;
import ru.spb.etu.client.ui.view.forms.EntityForm;
import ru.spb.etu.client.ui.widgets.HasValue;
import ru.spb.etu.client.ui.widgets.MyTextArea;
import ru.spb.etu.client.ui.widgets.MyTextBox;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class EntityEditPanel
    extends FlexTable
    implements
        AsyncCallback
{

    EntityWrapper     entityWrapper;
    ImageServiceAsync async = ImageService.App.getInstance();

    public EntityEditPanel()
    {
        super();
        entityWrapper = createEntityWrapper();
        entityWrapper.setImageUrl( getDefaultImageUrl() );

        int row = 0;
        // картинка
        EntityForm entityForm = new EntityForm( entityWrapper );
        createCenteredCell( row++, entityForm );
        // загрузка картинки
        createCenteredCell( row++, new FileUploadPanel( entityForm ) );

        createRow( row++, "Name", new MyTextBox(), entityWrapper.getTitle() );
        createRow( row++, "Description", new MyTextArea(), entityWrapper.getDescription() );
    }

    private void createCenteredCell(
        int row,
        Widget widget )
    {
        setWidget( row, 0, widget );
        getFlexCellFormatter().setColSpan( row, 0, 2 );
        getFlexCellFormatter().setHorizontalAlignment( row, 0, HasHorizontalAlignment.ALIGN_CENTER );
    }

    private void createRow(
        int row,
        String name,
        final HasValue hasValue,
        final ReflectiveString entityAttribute )
    {
        setWidget( row, 0, new Label( name ) );
        setWidget( row, 1, (Widget) hasValue );
        hasValue.addChangeListener( new ChangeListener()
        {
            public void onChange(
                Widget arg0 )
            {
                entityAttribute.setString( hasValue.getText() );
                async.saveOrUpdate( entityWrapper, EntityEditPanel.this );
            }
        } );
    }

    protected abstract String getDefaultImageUrl();

    protected abstract EntityWrapper createEntityWrapper();

    public void onFailure(
        Throwable arg0 )
    {
        Window.alert( arg0.toString() );
    }

    public void onSuccess(
        Object arg0 )
    {
    }
}
