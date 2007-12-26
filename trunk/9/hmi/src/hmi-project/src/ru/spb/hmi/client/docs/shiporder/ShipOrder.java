package ru.spb.hmi.client.docs.shiporder;

import ru.spb.hmi.client.docs.DocumentPanel;
import ru.spb.hmi.client.docs.XPathPanel;

import com.google.gwt.user.client.ui.TextBox;

public class ShipOrder
    extends DocumentPanel
{
    public ShipOrder(
        String id )
    {
        super( id );

        XPathPanel root = new XPathPanel( "shiporder", "shiporder" );
        XPathPanel orderid = new XPathPanel( root, "orderid", "orderid", new TextBox() );
        root.add( orderid );
        XPathPanel shipto = new XPathPanel( root, "name", "name" );
        orderid.add( shipto );
        XPathPanel name = new XPathPanel( root, "name", "name", new TextBox() );
        shipto.add( name );
        XPathPanel address = new XPathPanel( root, "address", "address", new TextBox() );
        shipto.add( address );
        XPathPanel city = new XPathPanel( root, "city", "city", new TextBox() );
        shipto.add( city );
        XPathPanel country = new XPathPanel( root, "country", "country", new TextBox() );
        shipto.add( country );
    }
}
