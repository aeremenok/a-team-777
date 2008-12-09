package talkie.server.data;

import talkie.common.constants.Status;

public class User
{
    private UserStatusListenerCollection listenerCollection = null;
    private String                       login              = "";
    private String                       pass               = "";
    private int                          status             = Status.AWAY;

    public User(
        String login,
        String pass )
    {
        this.login = login;
        this.pass = pass;
    }

    public void addStatusListener(
        UserStatusListener listener )
    {
        initListenerCollection();
        listenerCollection.add( listener );
    }

    public String getLogin()
    {
        return login;
    }

    public String getPass()
    {
        return pass;
    }

    public int getStatus()
    {
        return status;
    }

    public void setLogin(
        String login )
    {
        this.login = login;
    }

    public void setPass(
        String pass )
    {
        this.pass = pass;
    }

    public void setStatus(
        int status )
    {
        int oldStatus = this.status;
        this.status = status;

        if ( oldStatus != status )
        {
            initListenerCollection();
            listenerCollection.fire( this );
        }
    }

    private void initListenerCollection()
    {
        if ( listenerCollection == null )
        {
            listenerCollection = new UserStatusListenerCollection();
        }
    }
}
