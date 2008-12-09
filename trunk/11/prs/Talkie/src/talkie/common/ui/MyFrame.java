package talkie.common.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MyFrame
    extends JFrame
{
    public MyFrame()
    {
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
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
        center();
        setVisible( true );
    }
}
