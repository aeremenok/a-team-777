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
    protected ImageServiceAsync async          = ImageService.App.getInstance();

    /**
     * ������� ������ �����
     */
    protected FlexTable         editTable      = new FlexTable();
    /**
     * ������ ��������� �������
     */
    protected TraversalPanel    traversalPanel = new TraversalPanel( this );
    /**
     * �������� � ���������
     */
    protected EntityForm        entityForm;

    // �������� ����
    private MyTextArea          description    = new MyTextArea();
    private MyTextBox           name           = new MyTextBox();

    /**
     * ������� ����� � �������
     */
    private int                 row            = 0;

    public EntityEditPanel()
    {
        super();
        setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );

        add( traversalPanel );
        add( editTable );

        editTable.setVisible( false );

        // �������� � ���������
        createCenteredCell( getEntityForm() );
        // �������� ��������
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
                // ���������� ������������ ���
                traversalPanel.updateName( name );
            }
        } );
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
        getEntityForm().setEntityWrapper( entityWrapper );
    }

    /**
     * �������� � ������� � ������������ ������
     * 
     * @param widget ��� ��������
     */
    private void createCenteredCell(
        Widget widget )
    {
        row++;
        editTable.setWidget( row, 0, widget );
        editTable.getFlexCellFormatter().setColSpan( row, 0, 2 );
        editTable.getFlexCellFormatter().setHorizontalAlignment( row, 0, HasHorizontalAlignment.ALIGN_CENTER );
    }

    /**
     * ������� ������ ��� ������ ����
     * 
     * @param name ��� ����
     * @param hasValue ������ ��� ������ ����
     */
    protected void createRow(
        String name,
        HasValue hasValue )
    {
        row++;
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
