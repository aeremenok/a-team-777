/**
 *  ласс ”чащийс€
 */
public class Scholar
{
    /**
     * им€ учащегос€
     */
    private String name = null;

    Scholar( String name )
    {
        this.name = name;
    }

    /**
     * возвращает им€ учащегос€
     *
     * @return им€ учащегос€
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * мен€ет учащемус€ им€
     *
     * @param name новое им€ учащегос€
     */
    public void setName( String name )
    {
        if ( name != null )
        {
            this.name = name;
        }
    }

    /**
     * выводит информацию об учащемс€
     */
    public void printInfo()
    {
        System.out.println( "Scholar " + getName() + " awaiting for your orders, Sir!" );
    }
}
