/**
 * ����� �������
 */
public class Student
        extends Scholar
{
    Student( String name )
    {
        super( name );
    }

    /**
     * ������� ���������� � ��������
     */
    public void printInfo()
    {
        System.out.println( "Student " + getName() + " awaiting for your orders, Sir!" );
    }
}
