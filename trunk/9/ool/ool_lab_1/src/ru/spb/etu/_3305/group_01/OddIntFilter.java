package ru.spb.etu._3305.group_01;

/**
 * ������ �������� �����
 */
public class OddIntFilter
    implements
        IntFilter
{
    public boolean accepts(
        int value )
    {
        return value % 2 != 0;
    }
}
