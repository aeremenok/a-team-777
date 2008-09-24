package ru.spb.epa;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Павел
 * Date: 08.12.2007
 * Time: 21:22:51
 * for user auth
 */
public class UserManager {

    List<User> users = new ArrayList<User>();




    public UserManager() {

        if(ru.spb.epa.ServerConfig.IS_DEBUG){
            User u = new User("user", "user123");
            users.add(u);
        }

    }

    /**
     * Check if user login/pass correct
     * @param l user login
     * @param p user password
     * @return obj User if authenticate, null otherwise
     */
    public User auth(String l, String p){
        User u = new User(l,p);
        if(users.contains(u)) return u;
        return null;
    }
    




}
