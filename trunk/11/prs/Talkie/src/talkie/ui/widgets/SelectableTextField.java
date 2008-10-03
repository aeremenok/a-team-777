package talkie.ui.widgets;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class SelectableTextField
    extends JTextField
{
    private FocusAdapter focusListener = new FocusAdapter()
                                       {
                                           @Override
                                           public void focusGained(
                                               FocusEvent e )
                                           {
                                               SelectableTextField.this.selectAll();
                                           }
                                       };

    public SelectableTextField(
        int columns )
    {
        super( columns );
        addFocusListener( focusListener );
    }
}
