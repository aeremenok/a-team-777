package ru.spb.etu.server.stubs;

import java.util.HashMap;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.server.EntityBackuper;

public class EntityBackuperStub
    implements
        EntityBackuper
{

    static HashMap<String, EntityWrapper> hashMap = new HashMap<String, EntityWrapper>();

    @Override
    public void saveOrUpdate(
        EntityWrapper entityWrapper )
    {
        hashMap.put( entityWrapper.getTitle().toString(), entityWrapper );
        System.out.println( entityWrapper );
    }

    public static EntityWrapper get(
        EntityWrapper.ReflectiveString key )
    {
        return hashMap.get( key.toString() );
    }

    public static EntityWrapper get(
        String key )
    {
        return hashMap.get( key );
    }
}
