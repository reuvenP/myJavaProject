package model.backend;

import entities.User;

/**
 * Created by reuvenp on 5/4/2016.
 */
public class userSingleton {
    static User instance = null;
    public final static User getInstance(){
        if (instance == null)
            instance = new User();
        return instance;
    }
    public static void setInstance(User user)
    {
        instance = user;
    }
}
