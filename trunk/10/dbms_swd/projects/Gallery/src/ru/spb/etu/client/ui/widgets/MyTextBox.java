package ru.spb.etu.client.ui.widgets;

import ru.spb.etu.client.serializable.ReflectiveString;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MyTextBox
    extends TextBox
    implements
        HasValue
{

    private ReflectiveString field;

    public MyTextBox()
    {
        super();
        addChangeListener( this );
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
        field.setString( getText() );
        field.updateHost();
    }
}
