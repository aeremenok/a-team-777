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
     * ������� ������ �����
     */
    FlexTable          editTable      = new FlexTable();
    /**
     * ������ ��������� �������
     */
    TraversalPanel     traversalPanel = new TraversalPanel( this );
    /**
     * �������� � ���������
     */
    EntityForm         entityForm     = new EntityForm();

    // �������� ����
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
        // �������� � ���������
        createCenteredCell( row++, entityForm );
        // �������� ��������
        createCenteredCell( row++, new FileUploadPanel( entityForm ) );

        createRow( row++, "Name", name );
        createRow( row++, "Description", description );

        name.addChangeListener( new ChangeListener()
        {
            public void onChange(
                Widget arg0 )
            {
                // ���������� ������������ ���
                traversalPanel.updateName( name );
            }
        } );
    }

    public EntityForm getEntityForm()
    {
        return entityForm;
    }

    /**
     * @return ��� ������ ���������
     */
    public abstract String entityTypeName();

    /**
     * �������� ��� ������ � �������
     * 
     * @param callback ����������, ����������� ������
     */
    public abstract void retreiveEntities(
        AsyncCallback callback );

    /**
     * ��������� �������� � ���� �� ������ � �������� ���������
     * 
     * @param entityWrapper ��������
     */
    public void showEntity(
        EntityWrapper entityWrapper )
    {
        name.bindField( entityWrapper.getTitle() );
        description.bindField( entityWrapper.getDescription() );
        entityForm.setEntityWrapper( entityWrapper );
    }

    /**
     * �������� � ������� � ������������ ������
     * 
     * @param row ���� ��������
     * @param widget ��� ��������
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
     * ������� ������ ��� ������ ����
     * 
     * @param row ��� �������
     * @param name ��� ����
     * @param hasValue ������ ��� ������ ����
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
     * @return ���� � ����������� �������
     */
    public abstract String getDefaultImageUrl();

    /**
     * �������� ������� ������ (�� ��������� - ��������, ���� �� ��������)
     * 
     * @param show ��������/��������
     */
    public void showEditTable(
        boolean show )
    {
        editTable.setVisible( show );
    }
}
