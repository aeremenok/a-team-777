/**
 * ����� ��������
 */
public class Schoolboy
        extends Scholar
{
    Schoolboy( String name )
    {
        super( name );
    }

    /**
     * ������� ���������� � ���������
     */
    public void printInfo()
    {
        System.out.println( "Schoolboy " + getName() + " awaiting for your orders, Sir!" );
    }
}
