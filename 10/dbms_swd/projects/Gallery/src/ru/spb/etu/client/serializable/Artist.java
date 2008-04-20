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
    implements
        EntityWrapper
{
    Date   birthDate;
    String country;
    String description;
    String imageUrl;
    String name;

    public Artist()
    {
    }

    public Artist(
        Date birthDate,
        String country,
        String description,
        String name,
        String imageUrl )
    {
        super();
        this.birthDate = birthDate;
        this.country = country;
        this.description = description;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public String getCountry()
    {
        return country;
    }

    public String getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getName()
    {
        return name;
    }

    public String getTitle()
    {
        return getName();
    }

    public void setBirthDate(
        Date birthDate )
    {
        this.birthDate = birthDate;
    }

    public void setCountry(
        String country )
    {
        this.country = country;
    }

    public void setDescription(
        String description )
    {
        this.description = description;
    }

    public void setImageUrl(
        String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    public void setName(
        String name )
    {
        this.name = name;
    }

    public void requestMasterPieces()
    {
        ArrayList artists = new ArrayList();
        artists.add( this );
        ViewPanel.getInstance().setWidget( ResultPanel.getInstance().setArtists( artists ) );
    }

    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
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
        if ( name == null )
        {
            if ( other.name != null )
                return false;
        }
        else if ( !name.equals( other.name ) )
            return false;
        return true;
    }
}
