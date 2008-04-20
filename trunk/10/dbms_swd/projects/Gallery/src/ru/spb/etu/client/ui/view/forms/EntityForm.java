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
    protected EntityWrapper entityWrapper;

    private PopupPanel      descriptionPanel;

    protected Image         image;

    public EntityForm(
        EntityWrapper entityWrapper )
    {
        this.entityWrapper = entityWrapper;

        VerticalPanel verticalPanel = new VerticalPanel();
        image = new Image( entityWrapper.getImageUrl() );
        image.addStyleName( "small" );
        verticalPanel.add( image );
        verticalPanel.add( new Label( entityWrapper.getTitle() ) );
        setWidget( verticalPanel );

        addMouseListener( new MouseListenerAdapter()
        {
            public void onMouseEnter(
                Widget sender )
            {
                // image.addStyleName( "selected" );
                getDescriptionPanel().setPopupPosition( image.getAbsoluteLeft() + image.getWidth(), getAbsoluteTop() );
                getDescriptionPanel().show();
            }

            public void onMouseLeave(
                Widget sender )
            {
                // image.removeStyleName( "selected" );
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
        if ( descriptionPanel == null )
        {
            descriptionPanel = new PopupPanel( true );
            descriptionPanel.addStyleName( "gwt-DialogBox" );

            VerticalPanel verticalPanel = new VerticalPanel();
            verticalPanel.add( new HTML( entityWrapper.getTitle() ) );
            // todo verticalPanel.add( new Image( entityWrapper.getImageUrl() ) );
            verticalPanel.add( new HTML( entityWrapper.getDescription() ) );

            descriptionPanel.setWidget( verticalPanel );
        }
        return descriptionPanel;
    }
}
