package ru.spb.client.entities;

public interface IConnectable {

    public abstract boolean isConnected();

    public abstract void disconnect();

    public abstract void connect();

}