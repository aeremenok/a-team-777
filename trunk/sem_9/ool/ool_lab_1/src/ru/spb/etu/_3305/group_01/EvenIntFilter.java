package ru.spb.etu._3305.group_01;

/**
 * Created by IntelliJ IDEA. User: ssv Date: 10.10.2007 Time: 11:49:20 To change this template use File | Settings |
 * File Templates.
 */

/**
 * Фильтр чётных чисел
 */
public class EvenIntFilter
        implements IntFilter
{
    public boolean accepts( int value )
    {
        return value % 2 == 0;
    }
}
