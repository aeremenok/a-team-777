package ru.spb.etu._3305.group_01;

/**
 * Фильтр чётных чисел
 */
public class EvenIntFilter
    implements
        IntFilter
{
    public boolean accepts(
        int value )
    {
        return value % 2 == 0;
    }
}
