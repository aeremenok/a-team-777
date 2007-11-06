/**
 * Класс Студент
 */
public class Student
        extends Scholar
{
    Student( String name )
    {
        super( name );
    }

    /**
     * выводит информацию о студенте
     */
    public void printInfo()
    {
        System.out.println( "Student " + getName() + " awaiting for your orders, Sir!" );
    }
}
