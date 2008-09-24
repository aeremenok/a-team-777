/**
 * �������� ������
 * 
 * @author lysenko
 */
public class BinaryTree
{

    /**
     * ������ ���������
     */
    private BinaryTree  _rightSubtree;

    /**
     * ����� ���������
     */
    private BinaryTree  _leftSubtree;

    /**
     * ���� (��������� �� ��������� ��������� �������� ������)
     */
    private final short _key;

    /**
     * ������������� ���������� ���������� ����� � �������-��������� ������
     */
    private static int  _receiverIndex = 0;

    /**
     * @param k - ���� (�������� ��������� ��������)
     */
    public BinaryTree(
        short k )
    {
        _key = k;
    }

    /**
     * �������� ���������
     * 
     * @param aTree - ����������� ���������
     */
    public void add(
        BinaryTree aTree )
    {

        // �������� ���� ������������ ��������� (�) � ������ ��������� ���� (X)

        // ���� K >= X
        if ( aTree._key >= _key )
        {
            // ���������� �������� ����� ������ � ������ ���������
            if ( _rightSubtree != null )
            {
                _rightSubtree.add( aTree );
            }
            else
            {
                _rightSubtree = aTree;
            }
        }
        else

        // ���������� �������� ����� ������ � ����� ���������
        if ( _leftSubtree != null )
        {
            _leftSubtree.add( aTree );
        }
        else
        {
            _leftSubtree = aTree;
        }
    }

    /**
     * ��������� ���������� ������ (����� ������)
     * 
     * @param destArray - ������ �������� ������������� ������
     */
    public void sort(
        Short[] destArray )
    {

        // ������� ������ ��������� ��� ����������� ������������ ����������
        _receiverIndex = 0;

        try
        {
            // ���������� ������ ����� ���������
            if ( _leftSubtree != null )
            {
                _leftSubtree.sort( destArray );
            }

            // �������� �������� ��������� ���� � �������������� �������
            // ���������
            destArray[_receiverIndex] = _key;
            _receiverIndex++;

            // ���������� ������ ������ ���������
            if ( _rightSubtree != null )
            {
                _rightSubtree.sort( destArray );
            }

        }
        catch ( ArrayIndexOutOfBoundsException e )
        {
            System.out.println( "\n\n\tWrong array passed as output container!\n\n" );
        }
    }
}
