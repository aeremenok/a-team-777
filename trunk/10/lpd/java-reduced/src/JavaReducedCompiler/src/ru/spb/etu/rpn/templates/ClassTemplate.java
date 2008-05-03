package ru.spb.etu.rpn.templates;

/**
 * ראבכמם class-פאיכא
 * 
 * @author eav
 */
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
