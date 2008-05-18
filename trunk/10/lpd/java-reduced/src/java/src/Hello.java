import java.util.Iterator;

public class Hello
    implements
        Iterable
{
    int field;

    public static void main(
        String[] args )
    {
        boolean condition = true;
        System.out.println( "hello" );
    }

    public Iterator iterator()
    {
        return null;
    }
}
