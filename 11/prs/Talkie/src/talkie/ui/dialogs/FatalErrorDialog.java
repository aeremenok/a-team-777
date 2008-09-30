package talkie.ui.dialogs;

import java.awt.Frame;
import java.awt.Label;

import talkie.ui.MyDialog;

public class FatalErrorDialog
    extends MyDialog
{
    public FatalErrorDialog(
        Frame owner,
        String string )
    {
        super( owner );
        setTitle( "������!" );

        Label label = new Label( string, Label.CENTER );
        add( "Center", label );
    }
}
