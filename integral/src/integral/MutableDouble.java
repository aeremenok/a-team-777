package integral;

public class MutableDouble
{
    private double value;

    public MutableDouble(
        double value )
    {
        this.value = value;
    }

    public void add(
        double add )
    {
        value += add;
    }

    public double getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + getValue();
    }
}
