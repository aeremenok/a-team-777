package ru.spb.etu.client.ui.widgets;

import ru.spb.etu.client.serializable.ReflectiveString;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class MyListBox
    extends ListBox
    implements
        HasValue
{
    private ReflectiveString field;

    public MyListBox()
    {
        super();
        addChangeListener( this );
    }

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

    public void bindField(
        ru.spb.etu.client.serializable.ReflectiveString field )
    {
        this.field = field;
        setText( field.toString() );
    }

    public void onChange(
        Widget arg0 )
    {
        if ( field != null )
        {
            field.setString( getText() );
            field.updateHost();
        }
    }

}
