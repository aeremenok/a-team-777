package ru.spb.etu._3305.group_01;

/**
 * Пустой фильтр, пропускающий любые числа
 */
public class BogusIntFilter
    implements
        IntFilter
{
    public boolean accepts(
        int value )
    {
        return true;
    }
}
