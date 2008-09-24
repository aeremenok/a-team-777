package life;

public class LifeData
{
    private boolean[][] fields;
    private boolean     changed;

    public LifeData(
        boolean[][] fields )
    {
        this.changed = false;
        this.fields = fields;
    }

    public boolean[][] getFields()
    {
        return fields;
    }

    public boolean isChanged()
    {
        return changed;
    }

    public void setChanged(
        boolean changed )
    {
        this.changed = changed;
    }

    public void setFields(
        boolean[][] fields )
    {
        this.fields = fields;
    }
}
