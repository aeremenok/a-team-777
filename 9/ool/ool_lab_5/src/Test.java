import java.util.Random;

/**
 * �������� ������
 * 
 * @author lysenko
 */
public class Test
{

    /**
     * ������ ��������� �����
     */
    private static Random _random          = null;

    /**
     * ������������ ����� �������
     */
    private static int    MAX_ARRAY_LENGTH = 255;

    /**
     * @param args
     */
    public static void main(
        String[] args )
    {

        int arrayLength = 0;

        // ���������������� ������ ��������� �����
        _random = new Random( System.currentTimeMillis() );

        // ������ ����� �������
        if ( args.length == 1 )
        {
            arrayLength = Integer.parseInt( args[0] );
        }
        else
        {
            arrayLength = (int) (_random.nextDouble() * MAX_ARRAY_LENGTH) + 1;
        }

        // ������� ������
        short[] toSortArray = new short[arrayLength];

        // ���������������� ������ ���������� �������
        for ( int i = 0; i < toSortArray.length; i++ )
        {
            toSortArray[i] = (short) _random.nextInt();
        }

        // ������� �������� ������ �� �����
        System.out.println( "\nInitial array \t(" + arrayLength + " elements):" );
        for ( int i = 0; i < toSortArray.length; i++ )
        {
            System.out.print( toSortArray[i] + " " );
        }
        System.out.println();

        // ��������� ������ ����������
        new ShellSort( toSortArray );
        new HoareSort( toSortArray );
        new BubbleSort( toSortArray );
        new BinarySort( toSortArray );
    }

}
