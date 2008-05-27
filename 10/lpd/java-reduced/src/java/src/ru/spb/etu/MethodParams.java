package ru.spb.etu;

import org.apache.bcel.classfile.Method;

/**
 * метод и класс, который его реализует
 * 
 * @author eav
 */
public class MethodParams
{
    Method method;
    String className;

    public MethodParams(
        Method method,
        String className )
    {
        super();
        this.method = method;
        this.className = className;
    }
}
