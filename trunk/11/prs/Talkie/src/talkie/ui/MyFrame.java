package talkie.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.HeadlessException;

public class MyFrame
    extends Frame
{
    public MyFrame()
        throws HeadlessException
    {
        setLayout( new BorderLayout() );
    }

    /**
     * переместить окно в центр экрана
     */
    public void center()
    {
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension size = getSize();
        setLocation( screenSize.width / 2 - size.width / 2, screenSize.height / 2 - size.height / 2 );
    }

    /**
     * подобрать размер, переместить окно в центр экрана и отобразить
     */
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
                System.exit( NORMAL );
                return false;
            default:
                return false;
        }
    }
}
