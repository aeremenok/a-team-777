package ru.spb.etu.client.ui.view.forms;

import ru.spb.etu.client.serializable.EntityWrapper;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
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
    private VerticalPanel   info  = new VerticalPanel();

    private HTML              title     = new HTML();

    protected EntityWrapper entityWrapper;

    protected Image         image = new Image();

    public EntityForm()
    {
        VerticalPanel verticalPanel = new VerticalPanel();
        image.addStyleName( "small" );
        verticalPanel.add( image );
        verticalPanel.add( title );
        setWidget( verticalPanel );

        addMouseListener( new MouseListenerAdapter()
        {
            public void onMouseEnter(
                Widget sender )
            {
                getInfoPopup().show();
            }

            public void onMouseLeave(
                Widget sender )
            {
                getInfoPopup().hide();
            }
        } );
    }

    public EntityForm(
        EntityWrapper entityWrapper )
    {
        this();
        setEntityWrapper( entityWrapper );
    }

    /**
     * подксказка должна отображаться только одна
     */
    private static PopupPanel infoPopup = new PopupPanel( true );

    static
    {
        infoPopup.addStyleName( "gwt-DialogBox" );
    }

    public EntityWrapper getEntityWrapper()
    {
        return entityWrapper;
    }

    public PopupPanel getInfoPopup()
    {
        infoPopup.setPopupPosition( image.getAbsoluteLeft() + image.getWidth(), getAbsoluteTop() );
        infoPopup.setWidget( getInfoPanel() );
        return infoPopup;
    }

    public void setEntityWrapper(
        EntityWrapper entityWrapper )
    {
        this.entityWrapper = entityWrapper;
        image.setUrl( entityWrapper.getImageUrl() );
        title.setHTML( entityWrapper.getTitle().toString() );
        updateInfo();
    }

    public void setUrl(
        String results )
    {
        entityWrapper.setImageUrl( results );
        image.setUrl( results );
    }

    private VerticalPanel getInfoPanel()
    {
        if ( info.getWidgetCount() == 0 )
        {
            updateInfo();
        }
        return info;
    }

    private void updateInfo()
    {
        info.clear();
        info.add( new HTML( entityWrapper.getTitle().toString() ) );
        info.add( new HTML( entityWrapper.getDescription().toString() ) );
    }
}
