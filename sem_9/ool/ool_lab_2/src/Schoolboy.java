/**
 *  ласс Ўкольник
 */
public class Schoolboy
        extends Scholar
{
    Schoolboy( String name )
    {
        super( name );
    }

    /**
     * выводит информацию о школьнике
     */
    public void printInfo()
    {
        System.out.println( "Schoolboy " + getName() + " awaiting for your orders, Sir!" );
    }
}
