package ru.spb.messages;

import java.util.Properties;
import java.util.StringTokenizer;

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
        String description )
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

            _properties.put( CHANNEL, stringTokenizer.nextToken() );

            String topic = stringTokenizer.getRest();
            _properties.put( TOPIC, topic );
        }
        else if ( _type == RPL_NAMREPLY )
        {
            // RPL_NAMREPLY
            // "( "=" / "*" / "@" ) <channel>
            // :[ "@" / "+" ] <nick> *( " " [ "@" / "+" ] <nick> )
            StringTokenizer stringTokenizer = new StringTokenizer( _description, ":" );
            _properties.put( CHANNEL, stringTokenizer.nextToken() );
            if ( stringTokenizer.hasMoreTokens() )
            { // список не пуст
                _properties.put( NICKNAMES, stringTokenizer.nextToken() );
            }
            else
            {
                _properties.put( NICKNAMES, null );
            }
        }
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
