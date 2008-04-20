package ru.spb.etu.client.serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface EntityWrapper
    extends
        IsSerializable
{
    String getTitle();

    String getImageUrl();

    String getDescription();
}
