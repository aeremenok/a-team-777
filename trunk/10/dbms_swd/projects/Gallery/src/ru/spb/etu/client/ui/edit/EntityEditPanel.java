package ru.spb.etu.client.ui.edit;

import ru.spb.etu.client.ImageService;
import ru.spb.etu.client.ImageServiceAsync;
import ru.spb.etu.client.serializable.EntityWrapper;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public abstract class EntityEditPanel
    extends FlexTable
{

    EntityWrapper     entityWrapper;
    ImageServiceAsync async = ImageService.App.getInstance();
    private Image     image;

    public EntityEditPanel()
    {
        super();
        entityWrapper = createEntityWrapper();

        setWidget( 0, 0, new Label( "Name:" ) );
        TextBox textBox = new TextBox();
        setWidget( 0, 1, textBox );

        image = new Image( getDefaultImageUrl() );
        setWidget( 1, 0, image );
        getFlexCellFormatter().setColSpan( 1, 0, 2 );
        getFlexCellFormatter().setHorizontalAlignment( 1, 0, HasHorizontalAlignment.ALIGN_CENTER );

        setWidget( 2, 0, new FileUploadPanel( image ) );
        getFlexCellFormatter().setColSpan( 2, 0, 2 );
        getFlexCellFormatter().setHorizontalAlignment( 2, 0, HasHorizontalAlignment.ALIGN_CENTER );
    }

    protected abstract String getDefaultImageUrl();

    protected abstract EntityWrapper createEntityWrapper();
}
