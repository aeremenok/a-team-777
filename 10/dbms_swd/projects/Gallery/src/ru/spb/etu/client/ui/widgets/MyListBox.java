package ru.spb.etu.client.ui.widgets;

import com.google.gwt.user.client.ui.ListBox;

public class MyListBox
    extends ListBox
    implements
        HasValue
{
    public String getText()
    {
        return getValue( getSelectedIndex() );
    }

    public void setText(
        String arg0 )
    {
        for ( int i = 0; i < getItemCount(); i++ )
        {
            if ( getValue( i ).equals( arg0 ) )
            {
                setSelectedIndex( i );
                break;
            }
        }
    }

}
