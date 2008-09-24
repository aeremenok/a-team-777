package ru.spb.client;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * расширенный stringTokenizer
 * 
 * @author eav
 */
public class IRCStringTokenizer
    extends StringTokenizer
{
    private String    _delim;
    private String    _string;
    ArrayList<String> _tokens = new ArrayList<String>();

    public IRCStringTokenizer(
        String str,
        String delim )
    {
        super( str, delim );
        _string = str;
        _delim = delim;
        StringTokenizer tokenizer = new StringTokenizer( str, delim );
        while ( tokenizer.hasMoreTokens() )
        {
            _tokens.add( tokenizer.nextToken() );
        }
    }

    /**
     * получить хвост, без разбора, как он есть
     * 
     * @return
     */
    public String getRest()
    {
        StringBuffer res = new StringBuffer( "" );
        while ( hasMoreTokens() )
        {
            res.append( nextToken() ).append( " " );
        }
        return res.toString();
    }

    public String getString()
    {
        return _string;
    }

    public ArrayList<String> getTokens()
    {
        return _tokens;
    }

    public String get(
        int index )
    {
        return _tokens.get( index );
    }

    public int indexOf(
        Object o )
    {
        return _tokens.indexOf( o );
    }

    public int size()
    {
        return _tokens.size();
    }

    public String getRest(
        int channelPos )
    {
        for ( int i = 0; i < channelPos; ++i )
        {
            nextToken();
        }
        return getRest();
    }

}
