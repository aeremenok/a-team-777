package ru.spb.etu.client.serializable;

public class Painting
    extends MasterPiece
{
    ReflectiveString width  = new ReflectiveString( this );
    ReflectiveString height = new ReflectiveString( this );

    public ReflectiveString getWidth()
    {
        return width;
    }

    public void setWidth(
        int width )
    {
        this.width.setInt( width );
    }

    public ReflectiveString getHeight()
    {
        return height;
    }

    public void setHeight(
        int height )
    {
        this.height.setInt( height );
    }
}
