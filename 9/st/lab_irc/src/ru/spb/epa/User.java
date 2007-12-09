package ru.spb.epa;

/**
 * User: Павел
 * Date: 08.12.2007
 * Time: 21:23:21
 * for easy user managemant
 */
public class User {

    private String login;
    private String password;



    public User(String password, String login) {
        this.password = password;
        this.login = login;
    }


    public boolean equals(Object obj) {
        if(! (obj instanceof User)) return false;
        User u = (User) obj;
        return this.login != null && u.login != null &&
                this.login.equals(u.login);

    }
}
