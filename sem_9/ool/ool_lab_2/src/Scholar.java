/**
 * ����� ��������
 */
public class Scholar
{
    /**
     * ��� ���������
     */
    private String name = null;

    Scholar( String name )
    {
        this.name = name;
    }

    /**
     * ���������� ��� ���������
     *
     * @return ��� ���������
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * ������ ��������� ���
     *
     * @param name ����� ��� ���������
     */
    public void setName( String name )
    {
        if ( name != null )
        {
            this.name = name;
        }
    }

    /**
     * ������� ���������� �� ��������
     */
    public void printInfo()
    {
        System.out.println( "Scholar " + getName() + " awaiting for your orders, Sir!" );
    }
}
