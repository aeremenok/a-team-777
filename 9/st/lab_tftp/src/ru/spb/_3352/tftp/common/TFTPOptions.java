package ru.spb._3352.tftp.common;

import java.util.Hashtable;

/**
 * днонкмхрекэмше ножхх гюопняю мю времхе/гюохяэ
 */
public class TFTPOptions
    extends Hashtable
{
    /**
     * ножхъ "пюглеп акнйю"
     */
    public final static String BLKSIZE = "blksize";

    /**
     * ножхъ "рюилюср"
     */
    public final static String TIMEOUT = "timeout";

    /**
     * ножхъ "пюглеп тюикю"
     */
    public final static String TSIZE   = "tsize";

    /**
     * йнмярпсйрнп он слнквюмхч
     */
    public TFTPOptions()
    {
        super();
    }

    /**
     * йнмярпсйрнп
     * 
     * @param мювюкэмюъ блеярхлнярэ
     */
    public TFTPOptions(
        int initialCapacity )
    {
        super( initialCapacity );
    }

    /**
     * бнгбпюыюер ножхч "пюглеп акнйю" хг RFC2348
     * 
     * @return пюглеп акнйю хкх -1, еякх ме сйюгюм
     */
    public int getBlockSize()
    {
        return getIntegerOption( BLKSIZE );
    }

    /**
     * бнгбпюыюер жекне гмювемхе хг упюмъыеияъ б рейярнбнл бхде ярпнйх
     * 
     * @param option хлъ ножхх
     * @return integer гмювемхе ножхх хкх -1
     */
    public int getIntegerOption(
        String option )
    {
        String strValue = (String) get( option );

        if ( strValue == null )
        {
            return -1;
        }

        try
        {
            Integer value = Integer.decode( strValue );

            return value.intValue();
        }
        catch ( NumberFormatException e )
        {
            return -1;
        }
    }

    /**
     * бнгбпюыюер пюглеп ножхи б ртро-оюйере
     */
    public int getOptionsSize()
    {
        int size = 0;
        if ( hasOption( TIMEOUT ) )
        {
            size += TIMEOUT.length() + 1 + ("" + getTimeout()).length() + 1;
        }

        if ( hasOption( TSIZE ) )
        {
            size += TSIZE.length() + 1 + ("" + getTransferSize()).length() + 1;
        }

        if ( hasOption( BLKSIZE ) )
        {
            size += BLKSIZE.length() + 1 + ("" + getBlockSize()).length() + 1;
        }

        return size;
    }

    /**
     * бнгбпюыюер ножхч "рюилюср" хг RFC2349
     * 
     * @return рюилюср б яейсмдюу хкх -1, еякх ме сйюгюм
     */
    public int getTimeout()
    {
        return getIntegerOption( TIMEOUT );
    }

    /**
     * бнгбпюыюер ножхч "пюглеп тюикю" хг RFC2349
     * 
     * @return пюглеп тюикю хкх -1, еякх ме сйюгюм
     */
    public int getTransferSize()
    {
        return getIntegerOption( TSIZE );
    }

    /**
     * опнбепйю, упюмхряъ кх рюйюъ ножхъ
     * 
     * @param name хлъ
     * @return TRUE, еякх рюйюъ ножхъ сйюгюмю, FALSE хмюве
     */
    public boolean hasOption(
        String name )
    {
        return (this.containsKey( name ));
    }

    /**
     * днаюбкемхе ножхх б мхфмел пецхярпе
     * 
     * @param option хлъ ножхх
     * @param value гмювемхе ножхх
     */
    public Object put(
        Object option,
        Object value )
    {
        if ( !(option instanceof String) )
        {
            return null;
        }

        if ( !(value instanceof String) )
        {
            return null;
        }

        return super.put( ((String) option).toLowerCase(), value );
    }

    /**
     * сярюмюбкхбюер ножхч "пюглеп акнйю" хг RFC2348
     * 
     * @param blksize пюглеп акнйю
     */
    public void setBlockSize(
        int blksize )
    {
        setIntegerOption( BLKSIZE, blksize );
    }

    /**
     * янупюмъер вхякнбсч ножхч б рейярнбнл бхде
     * 
     * @param option хлъ ножхх
     * @param value гмювемхе ножхх
     */
    public void setIntegerOption(
        String option,
        int value )
    {
        put( option, new Integer( value ).toString() );
    }

    /**
     * сярюмюбкхбюер ножхч "рюилюср" хг RFC2349
     * 
     * @param timeout рюилюср б яейсмдюу
     */
    public void setTimeout(
        int to )
    {
        setIntegerOption( TIMEOUT, to );
    }

    /**
     * сярюмюбкхбюер ножхч "пюглеп тюикю" хг RFC2349
     * 
     * @param tsize пюглеп тюикю
     */
    public void setTransferSize(
        int tsize )
    {
        setIntegerOption( TSIZE, tsize );
    }
}
