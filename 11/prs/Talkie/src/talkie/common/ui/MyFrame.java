package talkie.common.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MyFrame
    extends JFrame
{
    public MyFrame()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
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
}
