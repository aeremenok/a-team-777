package ru.spb.etu.server;

import ru.spb.etu.client.ImageService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImageServiceImpl
    extends RemoteServiceServlet
    implements
        ImageService
{
    @Override
    public String getHello()
    {
        return "hello";
    }
}
