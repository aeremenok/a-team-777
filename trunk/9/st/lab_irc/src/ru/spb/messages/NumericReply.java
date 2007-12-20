package ru.spb.messages;

import java.util.Properties;

import ru.spb.client.IRCStringTokenizer;
import ru.spb.messages.constants.Errors;
import ru.spb.messages.constants.Replies;

public class NumericReply
    extends ServiceMessage
{
    /**
     * зарезервированные ключи свойств
     */
    public static final String TOPIC        = "topic";
    public static final String CHANNEL      = "channel";
    public static final String NICKNAMES    = "nicks";

    /**
     * числовой тип сообщения {@link Replies}, {@link Errors}
     */
    private int                _type        = 0;
    /**
     * описание целиком
     */
    private String             _description = "";

    /**
     * детальные свойства отдельных сообщений
     */
    private Properties         _properties  = new Properties();

    public NumericReply(
        int type,
        String description,
        int startOffset )
    {
        super();
        this._type = type;
        this._description = description;

        // в особых случаях разбираем сообщения
        if ( // "<channel> <# visible> :<topic>"
        _type == RPL_LIST ||
        // "<channel> :<topic>"
                        _type == RPL_TOPIC )
        {
            IRCStringTokenizer stringTokenizer = new IRCStringTokenizer( _description, " :" );

            int channelPos = getChannelPos( stringTokenizer );
            if ( channelPos == -1 )
            { // не найдено имя канала
                channelPos = startOffset + 2;
            }
            _properties.put( CHANNEL, stringTokenizer.get( channelPos ) );

            int topicPos = getTopicPos( channelPos, stringTokenizer );
            if ( topicPos == -1 )
            {
                topicPos = channelPos + 1;
            }
            String topic = stringTokenizer.get( topicPos );
            _properties.put( TOPIC, topic );
        }
        else if ( _type == RPL_NAMREPLY )
        {
            // RPL_NAMREPLY
            // "( "=" / "*" / "@" ) <channel>
            // :[ "@" / "+" ] <nick> *( " " [ "@" / "+" ] <nick> )
            IRCStringTokenizer stringTokenizer = new IRCStringTokenizer( _description, " " );
            int channelPos = getChannelPos( stringTokenizer );

            _properties.put( CHANNEL, stringTokenizer.get( channelPos ) );
            _properties.put( NICKNAMES, stringTokenizer.getRest( channelPos + 1 ) );
        }
    }

    private int getTopicPos(
        int channelPos,
        IRCStringTokenizer stringTokenizer )
    {
        for ( int pos = channelPos; pos < stringTokenizer.getTokens().size(); pos++ )
        {
            if ( stringTokenizer.get( pos ).startsWith( ":" ) )
                return pos;
        }
        return -1;
    }

    private int getChannelPos(
        IRCStringTokenizer stringTokenizer )
    {
        int counter = 0;
        for ( String token : stringTokenizer.getTokens() )
        {
            if ( token.startsWith( "#" ) || token.startsWith( "&" ) || token.startsWith( "*" ) )
            {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    public int getType()
    {
        return _type;
    }

    public String getDescription()
    {
        return _description;
    }

    public String getProperty(
        String key )
    {
        return _properties.getProperty( key );
    }
}
