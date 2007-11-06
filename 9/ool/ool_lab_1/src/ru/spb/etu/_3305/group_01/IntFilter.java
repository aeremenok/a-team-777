package ru.spb.etu._3305.group_01;

/**
 * Created by IntelliJ IDEA. User: ssv Date: 10.10.2007 Time: 11:55:50 To change this template use File | Settings |
 * File Templates.
 */

/**
 * Интерфейс для фильтрации целых чисел
 */
public interface IntFilter
{
    /**
     * Проверяет, удовлетворяет ли число условиям фильтра
     *
     * @param value число
     *
     * @return число удовлетворяет условиям
     */
    boolean accepts( int value );
}
