package ru.spb.messages;

public class QuitMessage
    extends ServiceMessage
{
    public QuitMessage()
    {
        _message = "QUIT";
    }
}
