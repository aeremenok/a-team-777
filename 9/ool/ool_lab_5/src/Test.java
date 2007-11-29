import java.util.Random;

/**
 * Òåñòîâûé ìîäóëü
 * 
 * @author lysenko
 */
public class Test
{

    /**
     * Äàò÷èê ñëó÷àéíûõ ÷èñåë
     */
    private static Random _random          = null;

    /**
     * Ìàêñèìàëüíàÿ äëèíà ìàññèâà
     */
    private static int    MAX_ARRAY_LENGTH = 255;

    /**
     * @param args
     */
    public static void main(
        String[] args )
    {

        int arrayLength = 0;

        // ÈÍÈÖÈÀËÈÇÈĞÎÂÀÒÜ ÄÀÒ×ÈÊ ÑËÓ×ÀÉÍÛÕ ×ÈÑÅË
        _random = new Random( System.currentTimeMillis() );

        // ÇÀÄÀÒÜ ÄËÈÍÓ ÌÀÑÑÈÂÀ
        if ( args.length == 1 )
        {
            arrayLength = Integer.parseInt( args[0] );
        }
        else
        {
            arrayLength = (int) (_random.nextDouble() * MAX_ARRAY_LENGTH) + 1;
        }

        // ÑÎÇÄÀÒÜ ÌÀÑÑÈÂ
        short[] toSortArray = new short[arrayLength];

        // ÈÍÈÖÈÀËÈÇÈĞÎÂÀÒÜ ÌÀÑÑÈÂ ÑËÓ×ÀÉÍÛÌÈ ×ÈÑËÀÌÈ
        for ( int i = 0; i < toSortArray.length; i++ )
        {
            toSortArray[i] = (short) _random.nextInt();
        }

        // ÂÛÂÅÑÒÈ ÈÑÕÎÄÍÛÉ ÌÀÑÑÈÂ ÍÀ İÊĞÀÍ
        System.out.println( "\nInitial array \t(" + arrayLength + " elements):" );
        for ( int i = 0; i < toSortArray.length; i++ )
        {
            System.out.print( toSortArray[i] + " " );
        }
        System.out.println();

        // ÇÀÏÓÑÒÈÒÜ ÏÎÒÎÊÈ ÑÎĞÒÈĞÎÂÊÈ
        new ShellSort( toSortArray );
        new HoareSort( toSortArray );
        new BubbleSort( toSortArray );
        new BinarySort( toSortArray );
    }

}
