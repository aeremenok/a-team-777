package ru.spb.etu.server.stubs;

import java.util.HashMap;

import ru.spb.etu.client.serializable.EntityWrapper;
import ru.spb.etu.client.serializable.ReflectiveString;
import ru.spb.etu.server.EntityBackuper;

public class EntityBackuperStub
    implements
        EntityBackuper
{

    private static final String           PACK    = "ru.spb.etu.client.serializable";
    static HashMap<String, EntityWrapper> hashMap = new HashMap<String, EntityWrapper>();

    @Override
    public void saveOrUpdate(
        EntityWrapper entityWrapper )
    {
        hashMap.put( entityWrapper.getTitle().toString(), entityWrapper );
        System.out.println( entityWrapper );
    }

    public static EntityWrapper get(
        ReflectiveString key )
    {
        return hashMap.get( key.toString() );
    }

    public static EntityWrapper get(
        String key )
    {
        return hashMap.get( key );
    }

    @Override
    public EntityWrapper create(
        String type )
        throws Exception
    {
        try
        {
            return (EntityWrapper) Class.forName( PACK + "." + type ).newInstance();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void remove(
        EntityWrapper entityWrapper )
    {
        hashMap.remove( entityWrapper );
    }

    @Override
    public void updateBlob(
        EntityWrapper entityWrapper,
        byte[] bytes )
    {
        // TODO Auto-generated method stub

    }
}
