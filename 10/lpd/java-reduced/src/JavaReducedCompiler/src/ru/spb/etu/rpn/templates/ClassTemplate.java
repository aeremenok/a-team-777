package ru.spb.etu.rpn.templates;

import org.gjt.jclasslib.structures.ClassFile;

/**
 * ראבכמם class-פאיכא
 * 
 * @author eav
 * @deprecated use {@link ClassFile}
 */
@Deprecated
public class ClassTemplate
    extends FileTemplate
{
    String name;
    int    number = 0;

    public ClassTemplate(
        Object object )
    {
    }

    public String getFileName()
    {
        if ( isNested() )
            return getName() + "$" + getNumber();
        return getName();
    }

    public String getName()
    {
        return name;
    }

    private int getNumber()
    {
        return number;
    }

    private boolean isNested()
    {
        return number > 0;
    }

}
