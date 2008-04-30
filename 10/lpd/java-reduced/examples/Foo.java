// интерфейсы
interface IThing
{
    abstract void doSomethingAbstract();
}

public class Foo
{
    public static void main(
        String[] args )
    {
		// todo что-то вызвать
    }

    // интерфейс-наследник
    interface IBar
        extends
            IThing
    {
        void doSomething(
            byte a,
            short b,
            int c,
            long d );

        IBar getInstance();
    }

    // просто функции
    private void count()
    {
        int i = 100;
        while ( i > 0 )
        {
            i--;
            --i;
        }

        char c = 'g';
        boolean condition = true;
        do
        {
            c = (char) i; // todo кр€коз€бры или эксепшен?
            System.out.println( c );
            if ( i++ > 25 )
            {
                condition = false;
            }
        }
        while ( condition );
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
            doSomething( (byte) 7, (short) 0, 0, 98L );
            this.getInstance().doSomethingAbstract();
        }

        public IBar getInstance()
        {
            return new ConcreteBar( (byte) 17, 0 );
        }
    }

    // абстрактный класс
    abstract static class AbstractBar
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
            this( (byte) 8, 9 );
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
            long d = 1000000000000000L;
            this.getInstance().doSomething( a, b, c, d );
        }

        abstract float getFloat();

        double getDouble()
        {
            return 9.1119238423d;
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
            return (float) 10.5;
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
            return new SimpleBar();
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
            IBar[] bars = getBars();
            for ( int i = 0; i < bars.length; i++ )
            {
                IBar bar = bars[i];
                System.out.println( bar );
            }
        }

        private IBar[] getBars()
        {
            return new IBar[] { new SimpleBar(), new ConcreteBar( (byte) (a + 1), c + 2 ),
                            new BarOverriden( (byte) 8, 7 ), new ConcreteBar() };
        }
    }
}
