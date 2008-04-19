package ru.spb.etu.client.serializable;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Artist
    implements
        IsSerializable
{
    Date   birthDate;
    String country;
    String description;
    String name;

    public Artist()
    {
    }

    public Artist(
        String name,
        Date birthDate,
        String country,
        String description )
    {
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
        this.description = description;
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

    public String getName()
    {
        return name;
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

    public void setName(
        String name )
    {
        this.name = name;
    }
}
