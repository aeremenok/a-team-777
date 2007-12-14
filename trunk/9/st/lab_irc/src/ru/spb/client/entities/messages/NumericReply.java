package ru.spb.client.entities.messages;

import java.util.Properties;
import java.util.StringTokenizer;

import ru.spb.messages.constants.Errors;
import ru.spb.messages.constants.Replies;

public class NumericReply
    extends ServiceMessage
{
    public static final String TOPIC        = "topic";
    public static final String CHANNEL      = "channel";
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
        if ( _type == RPL_LIST )
        {
            // "<channel> <# visible> :<topic>"
            StringTokenizer stringTokenizer = new StringTokenizer( _description, " " );

            _properties.put( CHANNEL, stringTokenizer.nextToken() );

            String topic = stringTokenizer.nextToken().substring( 1 );
            while ( stringTokenizer.hasMoreTokens() )
            {
                topic += " " + stringTokenizer.nextToken();
            }
            _properties.put( TOPIC, topic );

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
