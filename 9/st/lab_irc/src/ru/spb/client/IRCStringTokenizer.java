package ru.spb.client;

import java.util.StringTokenizer;

public class IRCStringTokenizer
    extends StringTokenizer
{

    private String _delim;
    private String string;

    public IRCStringTokenizer(
        String str,
        String delim )
    {
        super( str, delim );
        string = str;
        _delim = delim;
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
        return string;
    }

}
