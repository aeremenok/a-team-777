package ru.spb.etu.server;

import ru.spb.etu.client.serializable.EntityWrapper;

public interface EntityBackuper
{

    void saveOrUpdate(
        EntityWrapper entityWrapper );

    void remove(
        EntityWrapper entityWrapper );

    EntityWrapper create(
        String type )
        throws Exception;

    void updateBlob(
        EntityWrapper entityWrapper,
        byte[] bytes );

}
