package ru.spb.hmi.client.docs;

import com.google.gwt.user.client.ui.Label;

public class Doc2
    extends DocumentPanel
{

    public Doc2(
        String content )
    {
        super();
        setWidget( 1, 1, new Label( "doc 2" ) );
        xmlContent = content ;
        setWidget( 1, 2, new Label( xmlContent ) );
    }

}
