/**
 * ���������� ���������� �� ������ ��������� ������
 * 
 * @author lysenko
 */
public class BinarySort
    extends SortMethod
{

    /**
     * @param array - ����������� ������
     */
    public BinarySort(
        short[] array )
    {
        super( array );
    }

    /**
     * ��������� ���������� ������� �� ������ ��������� ������
     */
    protected void sort()
        throws InterruptedException
    {

        BinaryTree tree = new BinaryTree( _array[0] );

        // ��������� �������� ������
        for ( int i = 1; i < _array.length; i++ )
        {
            BinaryTree subTree = new BinaryTree( _array[i] );
            tree.add( subTree );
        }

        // ��������� ����������, �������� ��������� � �������� �������
        tree.sort( _array );
    }

    /**
     * @return ��� ������
     */
    protected String getMethodName()
    {
        return "Binary tree sort";
    }

}
