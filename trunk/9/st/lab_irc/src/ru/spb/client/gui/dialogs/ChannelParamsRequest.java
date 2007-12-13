package ru.spb.client.gui.dialogs;

import javax.swing.JTextArea;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.User;

/**
 * запрашивает параметры вновь создаваемого канала
 * 
 * @author eav
 */
public class ChannelParamsRequest
    extends ParamsRequest
{
    private Channel     _channel     = null;
    protected JTextArea _channelName = new JTextArea( "name" );

    public ChannelParamsRequest()
    {
        super( "Input channel paramenters" );
        _contentPane.add( _channelName );
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
        _channel = new Channel( _channelName.getText(), User.getCurrentUser() );
    }

    public Channel getChannel()
    {
        return _channel;
    }

}
