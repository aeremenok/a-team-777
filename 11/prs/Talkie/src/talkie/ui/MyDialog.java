package talkie.ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;

public class MyDialog
    extends Dialog
{
    public MyDialog(
        Frame owner )
    {
        super( owner );
        setModal( true );
    }

    public void display()
    {
        pack();
        center();
        setVisible( true );
    }

    @Override
    public boolean handleEvent(
        Event evt )
    {
        switch ( evt.id )
        {
            case Event.WINDOW_DESTROY:
                System.exit( 0 );
                return true;

            default:
                return false;
        }
    }

    private void center()
    {
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension size = getSize();
        setLocation( screenSize.width / 2 - size.width / 2, screenSize.height / 2 - size.height / 2 );
    }
}
