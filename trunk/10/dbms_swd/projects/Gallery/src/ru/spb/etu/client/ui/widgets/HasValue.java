package ru.spb.etu.client.ui.widgets;

import ru.spb.etu.client.serializable.ReflectiveString;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SourcesChangeEvents;

public interface HasValue
    extends
        HasText,
        SourcesChangeEvents,
        ChangeListener
{
    void bindField(
        ReflectiveString field );
}
