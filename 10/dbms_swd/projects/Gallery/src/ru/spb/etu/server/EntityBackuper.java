package ru.spb.etu.server;

import ru.spb.etu.client.serializable.EntityWrapper;

public interface EntityBackuper
{

    void saveOrUpdate(
        EntityWrapper entityWrapper );

}
