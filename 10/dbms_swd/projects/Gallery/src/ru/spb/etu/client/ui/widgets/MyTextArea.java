package ru.spb.etu.client.ui.widgets;

import ru.spb.etu.client.serializable.ReflectiveString;

import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class MyTextArea
    extends TextArea
    implements
        HasValue
{

    private ReflectiveString field;

    public MyTextArea()
    {
        super();
        addChangeListener( this );
    }

    public void bindField(
        ru.spb.etu.client.serializable.ReflectiveString field )
    {
        this.field = field;
    }

    public void onChange(
        Widget arg0 )
    {
        field.setString( getText() );
        field.updateHost();
    }

}
