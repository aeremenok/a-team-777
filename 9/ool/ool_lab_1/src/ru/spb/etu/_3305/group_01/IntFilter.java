package ru.spb.etu._3305.group_01;

/**
 * Интерфейс для фильтрации целых чисел
 */
public interface IntFilter
{
    /**
     * Проверяет, удовлетворяет ли число условиям фильтра
     * 
     * @param value число
     * @return число удовлетворяет условиям
     */
    boolean accepts(
        int value );
}
