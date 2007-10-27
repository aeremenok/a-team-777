/**
 * “очка входа
 */
public class Test
{
    public static void main( String[] args )
    {
        Scholar[] scholars = new Scholar[]{ new Student( "Petrov" ), new Schoolboy( "Sidorov" ),
                                            new Student( "Ivanov" ), new Scholar( "Pupkin" ),
                                            new Schoolboy( "Vasilyev" ) };

        // выводим всех
        System.out.println( "Scholars:" );
        for ( Scholar s : scholars )
        {
            s.printInfo();
        }
        System.out.println();

        // выводим только школьников
        System.out.println( "Schoolboys:" );
        for ( Scholar s : scholars )
        {
            if ( s instanceof Schoolboy )
            {
                s.printInfo();
            }
        }
        System.out.println();

        // выводим только студентов
        System.out.println( "Students:" );
        for ( Scholar s : scholars )
        {
            if ( s instanceof Student )
            {
                s.printInfo();
            }
        }
        System.out.println();
    }
}
