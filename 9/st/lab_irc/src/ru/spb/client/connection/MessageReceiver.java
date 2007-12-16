package ru.spb.client.connection;

import java.io.BufferedReader;

import ru.spb.client.entities.Channel;
import ru.spb.client.entities.Server;
import ru.spb.client.entities.User;
import ru.spb.client.gui.logpanels.ServiceLogPanel;
import ru.spb.messages.NumericReply;
import ru.spb.messages.PrivateMessage;
import ru.spb.messages.ServiceMessage;

/**
 * прослушивает входящие сообщения
 * 
 * @author eav
 */
public class MessageReceiver
    extends Thread
{
    private boolean _run = true;

    @Override
    public void interrupt()
    {
        _run = false;
    }

    /**
     * сервер, с которым работаем
     */
    private Server         _host;

    /**
     * прослушиваемый поток
     */
    private BufferedReader _reader;

    private ServiceMessage _currentRequest;

    MessageReceiver(
        BufferedReader reader,
        Server host )
    {
        _reader = reader;
        _host = host;
        start();
    }

    @Override
    public void run()
    {
        while ( _run )
        {
            try
            {
                String message = _reader.readLine();

                ServiceMessage serviceMessage = ServiceMessage.parse( message );
                if ( serviceMessage != null )
                { // ошибки не было

                    if ( serviceMessage instanceof PrivateMessage )
                    { // записываем в лог чата

                        PrivateMessage privateMessage = (PrivateMessage) serviceMessage;
                        // todo обобщить для пользователей
                        Channel channel = _host.getChannelByName( privateMessage.getTo() );
                        User user = channel.getUserByName( privateMessage.getFrom() );
                        user.say( privateMessage );
                    }
                    else
                    { // передаем для разбора

                        _currentRequest.receive( (NumericReply) serviceMessage );
                    }
                }
            }
            catch ( Throwable e )
            {
                ServiceLogPanel.getInstance().error( e.getMessage() );
                e.printStackTrace();
                _run = false;
                return;
            }
        }
    }

    @Override
    protected void finalize()
        throws Throwable
    {
        this.interrupt();
        super.finalize();
    }

    /**
     * @param request на это сообщение будет отправлен ближайший ответ
     */
    public synchronized void reply(
        ServiceMessage request )
    {
        _currentRequest = request;
    }
}
