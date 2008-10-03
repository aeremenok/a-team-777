package talkie.ui.dialogs;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import talkie.ui.MyDialog;

public class FatalErrorDialog
    extends MyDialog
{
    public FatalErrorDialog(
        JFrame owner,
        String string )
    {
        super( owner );
        setTitle( "Œÿ»¡ ¿!" );

        JLabel label = new JLabel( string, SwingConstants.CENTER );
        add( "Center", label );
    }
}
