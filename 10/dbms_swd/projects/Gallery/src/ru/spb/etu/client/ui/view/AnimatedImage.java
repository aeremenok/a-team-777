package ru.spb.etu.client.ui.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.Widget;

public class AnimatedImage
    extends Image
    implements
        LoadListener
{
    public AnimatedImage(
        String url )
    {
        super( url );
        setVisible( false );
        addLoadListener( this );
        setUrl( url );
    }

    class ImageTimer
        extends Timer
    {
        int step = 0;

        public ImageTimer(
            Image image )
        {
            setVisible( true );
        }

        public void run()
        {
            step += 5;
            setWidth( step + "%" );
            if ( step == 100 )
            {
                cancel();
            }
        }
    }

    public void onLoad(
        Widget arg0 )
    {
        new ImageTimer( this ).scheduleRepeating( 1 );
        setVisible( true );
    }

    public void onError(
        Widget arg0 )
    {
    }
}
