package ru.spb._3352.tftp.common;

import java.util.Hashtable;

/**
 * �������������� ����� ������� �� ������/������
 */
public class TFTPOptions
    extends Hashtable
{
    /**
     * ����� "������ �����"
     */
    public final static String BLKSIZE = "blksize";

    /**
     * ����� "�������"
     */
    public final static String TIMEOUT = "timeout";

    /**
     * ����� "������ �����"
     */
    public final static String TSIZE   = "tsize";

    /**
     * ����������� �� ���������
     */
    public TFTPOptions()
    {
        super();
    }

    /**
     * �����������
     * 
     * @param ��������� �����������
     */
    public TFTPOptions(
        int initialCapacity )
    {
        super( initialCapacity );
    }

    /**
     * ���������� ����� "������ �����" �� RFC2348
     * 
     * @return ������ ����� ��� -1, ���� �� ������
     */
    public int getBlockSize()
    {
        return getIntegerOption( BLKSIZE );
    }

    /**
     * ���������� ����� �������� �� ���������� � ��������� ���� ������
     * 
     * @param option ��� �����
     * @return integer �������� ����� ��� -1
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
     * ���������� ������ ����� � ����-������
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
     * ���������� ����� "�������" �� RFC2349
     * 
     * @return ������� � �������� ��� -1, ���� �� ������
     */
    public int getTimeout()
    {
        return getIntegerOption( TIMEOUT );
    }

    /**
     * ���������� ����� "������ �����" �� RFC2349
     * 
     * @return ������ ����� ��� -1, ���� �� ������
     */
    public int getTransferSize()
    {
        return getIntegerOption( TSIZE );
    }

    /**
     * ��������, �������� �� ����� �����
     * 
     * @param name ���
     * @return TRUE, ���� ����� ����� �������, FALSE �����
     */
    public boolean hasOption(
        String name )
    {
        return (this.containsKey( name ));
    }

    /**
     * ���������� ����� � ������ ��������
     * 
     * @param option ��� �����
     * @param value �������� �����
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
     * ������������� ����� "������ �����" �� RFC2348
     * 
     * @param blksize ������ �����
     */
    public void setBlockSize(
        int blksize )
    {
        setIntegerOption( BLKSIZE, blksize );
    }

    /**
     * ��������� �������� ����� � ��������� ����
     * 
     * @param option ��� �����
     * @param value �������� �����
     */
    public void setIntegerOption(
        String option,
        int value )
    {
        put( option, new Integer( value ).toString() );
    }

    /**
     * ������������� ����� "�������" �� RFC2349
     * 
     * @param timeout ������� � ��������
     */
    public void setTimeout(
        int to )
    {
        setIntegerOption( TIMEOUT, to );
    }

    /**
     * ������������� ����� "������ �����" �� RFC2349
     * 
     * @param tsize ������ �����
     */
    public void setTransferSize(
        int tsize )
    {
        setIntegerOption( TSIZE, tsize );
    }
}
