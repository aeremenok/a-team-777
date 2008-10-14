package integral;

/**
 * Integer, значение которого можно изменять
 * 
 * @author ssv
 */
public class MutableInteger
{
    private int value;

    public MutableInteger(
        int value )
    {
        this.value = value;
    }

    public void decrement()
    {
        value--;
    }

    public int getValue()
    {
        return value;
    }

    public void increment()
    {
        value++;
    }

    @Override
    public String toString()
    {
        return "" + getValue();
    }
}
