package ru.spb.etu.client.ui.view.forms;

import ru.spb.etu.client.serializable.MasterPiece;
import ru.spb.etu.client.ui.view.AnimatedImage;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * форма отображения произведений
 * 
 * @author eav
 */
public class MasterPieceForm
    extends EntityForm
{
    private CloseableDialog imagePanel;

    public MasterPieceForm(
        MasterPiece masterPiece )
    {
        super( masterPiece );

        image.addClickListener( new ClickListener()
        {
            public void onClick(
                Widget arg0 )
            {
                getImagePanel().show();
            }
        } );
    }

    class CloseableDialog
        extends DialogBox
        implements
            ClickListener
    {
        private AnimatedImage bigImage;

        public CloseableDialog(
            MasterPiece masterPiece )
        {
            super( false, true );
            setText( masterPiece.getTitle() + ": click on image to close" );

            bigImage = new AnimatedImage( masterPiece.getImageUrl() );
            bigImage.addClickListener( this );
            setWidget( bigImage );
        }

        public void show()
        {
            super.show();
            bigImage.onLoad( bigImage );
        }

        public void onClick(
            Widget arg0 )
        {
            hide();
        }
    }

    public CloseableDialog getImagePanel()
    {
        if ( imagePanel == null )
        {
            imagePanel = new CloseableDialog( (MasterPiece) entityWrapper );
            imagePanel.center();
        }
        return imagePanel;
    }
}
