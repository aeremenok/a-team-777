package ru.spb.etu._3305.group_01;

/**
 * Created by IntelliJ IDEA. User: ssv Date: 10.10.2007 Time: 12:03:10 To change this template use File | Settings |
 * File Templates.
 */

/**
 * Пустой фильтр, пропускающий любые числа
 */
public class BogusIntFilter
        implements IntFilter
{
    public boolean accepts( int value )
    {
        return true;
    }
}
