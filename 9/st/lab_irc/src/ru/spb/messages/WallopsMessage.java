package ru.spb.messages;

import java.util.ArrayList;

public class WallopsMessage
    extends ServiceMessage
{
    /**
     * сообщение, о котором уведомляют
     */
    private ServiceMessage    _serviceMessage;
    /**
     * имя автора сообщения
     */
    private String            _author;
    /**
     * имена каналов, в которые послано
     */
    private ArrayList<String> _channelNames;

    public WallopsMessage(
        ServiceMessage serviceMessage,
        String author,
        ArrayList<String> channelNames )
    {
        _serviceMessage = serviceMessage;
        _author = author;
        _channelNames = channelNames;
    }

    public ServiceMessage getServiceMessage()
    {
        return _serviceMessage;
    }

    public String getAuthor()
    {
        return _author;
    }

    public ArrayList<String> getСhannelNames()
    {
        return _channelNames;
    }
}
