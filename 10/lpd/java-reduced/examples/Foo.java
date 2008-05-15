// интерфейсы
interface IThing
{
    void doSomethingAbstract();
}

public class Foo
{
    public static void main(
        String/*[]*/ args )
    {
		// todo что-то вызвать
    }

    // интерфейс-наследник
    /*interface IBar
        extends
            IThing
    {
        void doSomething(
            byte a,
            short b,
            int c,
            long d );

        IBar getInstance();
    }*/

    // просто функции
    private void count()
    {
        int i = 100;
        while ( i > 0 )
        {
            /*i--;
            --i;*/
        }

        //char c = 'g';
        boolean condition = true;
        while ( condition )
        {
            c =  i; // todo кр€коз€бры или эксепшен?
            System.out.println( c );
            if ( i > 25 )
            {
                condition = false;
            }
        }
    }
}

// просто класс
public class SimpleBar
    implements
        IBar
{
    public void doSomething(
        byte a,
        short b,
        int c,
        long d )
    {
        count();
        System.out.println( "doing something" );
    }

    public void doSomethingAbstract()
    {
        doSomething( 7,  0, 0, 98 );
        this.getInstance().doSomethingAbstract();
    }

    public IBar getInstance()
    {
        return ConcreteBar(  17, 0 );
    }
}

// абстрактный класс
static class AbstractBar
    implements
        IBar
{
    private final short b = 7;
    byte                a;
    protected int       c;

    static float        staticFloat;
    static double       staticDouble;

    public AbstractBar()
    {
        this( 8, 9 );
        System.out.println( "constructor redirection" );
    }

    public AbstractBar(
        byte a,
        int c )
    {
        super();
        this.a = a;
        this.c = c;
        staticFloat = getFloat();
        staticDouble = getDouble();
    }

    public void doSomethingAbstract()
    {
        long d = 1000000000000000;
        this.getInstance().doSomething( a, b, c, d );
    }

    double getDouble()
    {
        return 9.1119238423;
    }
}

// конкретный класс
class ConcreteBar
    extends AbstractBar
{
    public ConcreteBar()
    {
        super();
    }

    public ConcreteBar(
        byte a,
        int c )
    {
        super( a, c );
    }

    float getFloat()
    {
        return 10.5;
    }

    public void doSomething(
        byte a,
        short b,
        int c,
        long d )
    {
        System.out.println( "a=" + a + "b=" + b + "c=" + c + "d=" + d );
    }

    public IBar getInstance()
    {
        return SimpleBar();
    }
}

// наследник с переопределением метода
public class BarOverriden
    extends ConcreteBar
{

    public BarOverriden(
        byte a,
        int c )
    {
        super( a, c );
    }

    public IBar getInstance()
    {
        IBar instance = super.getInstance();
        System.out.println( "getting instance" );
        return instance;
    }

    public void doSomething(
        byte a,
        short b,
        int c,
        long d )
    {
        super.doSomething( a, b, c, d );

        System.out.println( "static float:" + staticFloat );
        System.out.println( "static double:" + staticDouble );

        System.out.println( "different bars:" );
        IBar/*[]*/ bars = getBars();
   
    }

    //private IBar/*[]*/ getBars()
    //{
    //    return /*new*/ IBar/*[]*/ { new SimpleBar(), new ConcreteBar( (a + 1), c + 2 ),
    //                    new BarOverriden( 8, 7 ), new ConcreteBar() };
    //}
}
