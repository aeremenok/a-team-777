package ru.spb.etu.client.ui.edit;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.ui.view.forms.EntityForm;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public abstract class EntityEditPanel
    extends FlexTable
    implements
        AsyncCallback,
        ChangeListener
{

    EntityWrapper     entityWrapper;
    ImageServiceAsync async = ImageService.App.getInstance();

    public EntityEditPanel()
    {
        super();
        entityWrapper = createEntityWrapper();

        int row = 0;
        // картинка
        EntityForm entityForm = new EntityForm( entityWrapper );
        createCenteredCell( row++, entityForm );
        // загрузка картинки
        createCenteredCell( row++, new FileUploadPanel( entityForm ) );

        createRow( row++, "Name", new TextBox() );
        createRow( row++, "Description", new TextArea() );
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
        SourcesChangeEvents sourcesChangeEvents )
    {
        setWidget( row, 0, new Label( name ) );
        setWidget( row, 1, (Widget) sourcesChangeEvents );
        sourcesChangeEvents.addChangeListener( this );
    }

    public void onChange(
        Widget arg0 )
    {
        if ( !((TextBoxBase) arg0).getText().equals( "" ) )
        { // todo
            async.saveOrUpdate( entityWrapper, EntityEditPanel.this );
        }
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
