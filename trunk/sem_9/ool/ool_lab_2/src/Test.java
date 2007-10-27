/**
 * ����� �����
 */
public class Test
{
    public static void main( String[] args )
    {
        Scholar[] scholars = new Scholar[]{ new Student( "Petrov" ), new Schoolboy( "Sidorov" ),
                                            new Student( "Ivanov" ), new Scholar( "Pupkin" ),
                                            new Schoolboy( "Vasilyev" ) };

        // ������� ����
        System.out.println( "Scholars:" );
        for ( Scholar s : scholars )
        {
            s.printInfo();
        }
        System.out.println();

        // ������� ������ ����������
        System.out.println( "Schoolboys:" );
        for ( Scholar s : scholars )
        {
            if ( s instanceof Schoolboy )
            {
                s.printInfo();
            }
        }
        System.out.println();

        // ������� ������ ���������
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
