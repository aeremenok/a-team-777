package talkie.server.data;

import java.util.HashSet;

public class UserStatusListenerCollection
{
    private HashSet<UserStatusListener> listeners = new HashSet<UserStatusListener>();

    public void add(
        UserStatusListener listener )
    {
        listeners.add( listener );
    }

    public void clear()
    {
        listeners.clear();
    }

    public void fire(
        User user )
    {
        for ( UserStatusListener listener : listeners )
        {
            listener.fireStatusChange( user );
        }
    }

    public void remove(
        UserStatusListener listener )
    {
        listeners.remove( listener );
    }
}
