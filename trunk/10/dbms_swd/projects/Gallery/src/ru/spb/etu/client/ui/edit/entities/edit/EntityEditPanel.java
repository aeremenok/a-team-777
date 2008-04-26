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

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public abstract class EntityEditPanel
    extends VerticalPanel
{
    private MyTextArea description = new MyTextArea();
    private EntityForm entityForm;
    private MyTextBox  name        = new MyTextBox();
    ImageServiceAsync  async       = ImageService.App.getInstance();

    FlexTable          editTable   = new FlexTable();
    EntityWrapper      entityWrapper;
    TraversalPanel     traversalPanel;

    public EntityEditPanel()
    {
        super();
        traversalPanel = createTraversalPanel( this );
        add( traversalPanel );
        add( editTable );
        editTable.setVisible( false );

        int row = 0;
        entityForm = new EntityForm();
        createCenteredCell( row++, entityForm );
        // загрузка картинки
        createCenteredCell( row++, new FileUploadPanel( entityForm ) );

        createRow( row++, "Name", getName() );
        createRow( row++, "Description", getDescription() );

        getName().addChangeListener( new ChangeListener()
        {
            public void onChange(
                Widget arg0 )
            {
                traversalPanel.updateName( getName() );
            }
        } );
    }

    public EntityForm getEntityForm()
    {
        return entityForm;
    }

    public FlexCellFormatter getFlexCellFormatter()
    {
        return editTable.getFlexCellFormatter();
    }

    public void setWidget(
        int arg0,
        int arg1,
        Widget arg2 )
    {
        editTable.setWidget( arg0, arg1, arg2 );
    }

    public void showEntity(
        EntityWrapper entityWrapper )
    {
        getName().bindField( entityWrapper.getTitle() );
        getDescription().bindField( entityWrapper.getDescription() );
        getEntityForm().setEntityWrapper( entityWrapper );
        getEntityForm().setUrl( getDefaultImageUrl() );
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
        final HasValue hasValue )
    {
        setWidget( row, 0, new Label( name ) );
        setWidget( row, 1, (Widget) hasValue );
    }

    protected abstract TraversalPanel createTraversalPanel(
        EntityEditPanel entityEditPanel );

    protected abstract String getDefaultImageUrl();

    MyTextArea getDescription()
    {
        return description;
    }

    MyTextBox getName()
    {
        return name;
    }

    public FlexTable getEditTable()
    {
        return editTable;
    }
}
