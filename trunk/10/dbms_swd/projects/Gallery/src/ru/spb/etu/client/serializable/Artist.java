package ru.spb.etu.client.serializable;

import java.util.Date;

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
}
