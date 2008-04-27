package ru.spb.etu.client.serializable;

public class Sculpture
    extends MasterPiece
{
    ReflectiveString mass     = new ReflectiveString( this );
    ReflectiveString material = new ReflectiveString( this );

    public ReflectiveString getMass()
    {
        return mass;
    }

    public void setMass(
        int mass )
    {
        this.mass.setInt( mass );
    }

    public ReflectiveString getMaterial()
    {
        return material;
    }

    public void setMaterial(
        String material )
    {
        this.material.setString( material );
    }
}
