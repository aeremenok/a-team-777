package ru.spb.etu.client.serializable;

import java.util.ArrayList;
import java.util.Date;

import ru.spb.etu.client.ui.view.ResultPanel;
import ru.spb.etu.client.ui.view.ViewPanel;

/**
 * обертка данных художника
 * 
 * @author eav
 */
public class Artist
    extends EntityWrapper
{
    Date             birthDate;
    ReflectiveString country = new ReflectiveString( this );

    public Artist()
    {
        birthDate = new Date();
    }

    public Artist(
        Date birthDate,
        String country,
        String description,
        String name,
        String imageUrl )
    {
        super( description, imageUrl, name );
        setBirthDate( birthDate );
        setCountry( country );
    }

    public boolean equals(
        Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !(obj instanceof Artist) )
            return false;
        final Artist other = (Artist) obj;
        if ( getTitle() == null )
        {
            if ( other.getTitle() != null )
                return false;
        }
        else if ( !getTitle().equals( other.getTitle() ) )
            return false;
        return true;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public ReflectiveString getCountry()
    {
        return country;
    }

    public ReflectiveString getName()
    {
        return getTitle();
    }

    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getTitle() == null ? 0 : getTitle().hashCode());
        return result;
    }

    public void requestMasterPieces()
    {
        ArrayList artists = new ArrayList();
        artists.add( this );
        ViewPanel.getInstance().setWidget( ResultPanel.getInstance().setArtists( artists ) );
    }

    public void setBirthDate(
        Date birthDate )
    {
        this.birthDate = birthDate;
    }

    public void setCountry(
        String country )
    {
        this.country.setString( country );
    }

    public void setName(
        String name )
    {
        setTitle( name );
    }

    public void applyToMasterPiece(
        MasterPiece masterPiece )
    {
        masterPiece.setArtist( this );
    }
}
