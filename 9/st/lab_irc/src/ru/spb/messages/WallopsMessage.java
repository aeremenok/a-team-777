package ru.spb.messages;

public class WallopsMessage
    extends ServiceMessage
{
    /**
     * сообщение, о котором уведомляют
     */
    private ServiceMessage _serviceMessage;
    /**
     * имя автора сообщения
     */
    private String         _author;
    /**
     * имя канала, в который послано
     */
    private String         _channelName;

    public WallopsMessage(
        JoinMessage joinMessage,
        String author,
        String channelName )
    {
        _serviceMessage = joinMessage;
        _author = author;
        _channelName = channelName;
    }

    public ServiceMessage getServiceMessage()
    {
        return _serviceMessage;
    }

    public String getAuthor()
    {
        return _author;
    }

    public String getChannelName()
    {
        return _channelName;
    }

}
