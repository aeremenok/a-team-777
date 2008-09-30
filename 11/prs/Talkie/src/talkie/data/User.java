package talkie.data;

import java.util.ArrayList;

import talkie.Talkie;

public class User
{
    private String  login   = "";
    private String  pass    = "";
    private int     status  = Talkie.AWAY;
    ArrayList<User> friends = new ArrayList<User>();

    public User(
        String login,
        String pass )
    {
        this.login = login;
        this.pass = pass;
    }

    public boolean addFriend(
        User user )
    {
        if ( friends.contains( user ) )
        {
            return false;
        }
        else
        {
            friends.add( user );
        }
        return true;
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

    public boolean hasFriend(
        User user )
    {
        return friends.contains( user );
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
        this.status = status;
    }
}
