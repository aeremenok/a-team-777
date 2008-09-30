package talkie.ui.widgets;

import java.awt.TextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SelectableTextField
    extends TextField
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
