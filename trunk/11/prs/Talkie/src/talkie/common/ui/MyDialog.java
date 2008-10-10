package talkie.common.ui;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MyDialog
    extends JDialog
{
    public MyDialog(
        JFrame owner )
    {
        super( owner );
        setModal( true );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
    }

    public void display()
    {
        pack();
        center();
        setVisible( true );
    }

    private void center()
    {
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension size = getSize();
        setLocation( screenSize.width / 2 - size.width / 2, screenSize.height / 2 - size.height / 2 );
    }
}
