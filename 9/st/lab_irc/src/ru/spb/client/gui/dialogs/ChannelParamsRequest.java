package ru.spb.client.gui.dialogs;

import javax.swing.JTextArea;

import ru.spb.client.entities.Channel;

/**
 * запрашивает параметры вновь создаваемого канала
 * 
 * @author eav
 */
public class ChannelParamsRequest
    extends ParamsRequest
{
    private Channel     _channel = null;
    protected JTextArea _name    = new JTextArea( "name" );
    protected JTextArea _topic   = new JTextArea( "topic" );

    public ChannelParamsRequest()
    {
        super( "Input channel paramenters" );
        _contentPane.add( _name );
        pack();
    }

    public static Channel getNewChannel()
    {
        ChannelParamsRequest dialog = new ChannelParamsRequest();
        showOnCenter( dialog );

        return dialog.getChannel();
    }

    @Override
    protected void readParams()
    {
        _channel = new Channel( _name.getText(), _topic.getText() );
    }

    public Channel getChannel()
    {
        return _channel;
    }

}
