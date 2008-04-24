package ru.spb.etu.client.ui.view.forms;

import ru.spb.etu.client.serializable.EntityWrapper;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * форма отображения сущности
 * 
 * @author eav
 */
public class EntityForm
    extends FocusPanel
{
    protected EntityWrapper   entityWrapper;

    /**
     * подксказка должна отображаться только одна
     */
    private static PopupPanel descriptionPanel = new PopupPanel( true );

    static
    {
        descriptionPanel.addStyleName( "gwt-DialogBox" );
    }

    protected Image           image;

    private VerticalPanel     description;

    public EntityForm(
        EntityWrapper entityWrapper )
    {
        this.entityWrapper = entityWrapper;

        VerticalPanel verticalPanel = new VerticalPanel();
        image = new Image( entityWrapper.getImageUrl() );
        image.addStyleName( "small" );
        verticalPanel.add( image );
        verticalPanel.add( new Label( entityWrapper.getTitle().toString() ) );
        setWidget( verticalPanel );

        addMouseListener( new MouseListenerAdapter()
        {
            public void onMouseEnter(
                Widget sender )
            {
                getDescriptionPanel().show();
            }

            public void onMouseLeave(
                Widget sender )
            {
                getDescriptionPanel().hide();
            }
        } );
    }

    public EntityWrapper getEntityWrapper()
    {
        return entityWrapper;
    }

    public PopupPanel getDescriptionPanel()
    {
        descriptionPanel.setPopupPosition( image.getAbsoluteLeft() + image.getWidth(), getAbsoluteTop() );
        descriptionPanel.setWidget( getDescription() );
        return descriptionPanel;
    }

    private VerticalPanel getDescription()
    {
        if ( description == null )
        {
            description = new VerticalPanel();
            description.add( new HTML( entityWrapper.getTitle().toString() ) );
            // todo description.add( new Image( entityWrapper.getImageUrl() ) );
            description.add( new HTML( entityWrapper.getDescription().toString() ) );
        }
        return description;
    }

    public void setUrl(
        String results )
    {
        entityWrapper.setImageUrl( results );
        image.setUrl( results );
    }
}
