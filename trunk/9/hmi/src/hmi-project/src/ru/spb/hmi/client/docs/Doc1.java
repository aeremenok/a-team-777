package ru.spb.hmi.client.docs;

import com.google.gwt.user.client.ui.Label;

public class Doc1
    extends DocumentPanel
{

    public Doc1(
        String content )
    {
        super();
        setWidget( 1, 1, new Label( "doc 1" ) );
        xmlContent = content;
        setWidget( 1, 2, new Label( xmlContent ) );
    }

}
