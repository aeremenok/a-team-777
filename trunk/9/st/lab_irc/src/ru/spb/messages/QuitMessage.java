package ru.spb.messages;

public class QuitMessage
    extends ServiceMessage
{
    static
    {
        // нет возможных ошибок, как нет ответов вообще
    }

    public QuitMessage()
    {
        _message = "QUIT";
    }
}
