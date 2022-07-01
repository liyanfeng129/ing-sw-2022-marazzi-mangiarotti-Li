package it.polimi.ingsw.client;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.InnerExceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Users implements Serializable {
    List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public Users()
    {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }


    public boolean contains(String user)
    {
        for(User u: users)
            if(u.getName().equals(user))
                return true;
        return false;
    }

    public void addUser(String userName, boolean isLogged)
    {
        this.users.add(new User(userName, isLogged));
    }

    public void logUser(String userName) throws EriantysExceptions {
        users.get(users.indexOf(getUser(userName))).logging();
    }

    public void logOutUser(String userName) throws EriantysExceptions {
        users.get(users.indexOf(getUser(userName))).logOut();
    }

    public User getUser(String user) throws EriantysExceptions
    {
        for(User u: users)
            if(u.getName().equals(user))
                return u;
        throw new InnerExceptions.NoSuchUserException("user not found");
    }
    public void logOutAll()
    {
        for(User u: users)
            u.logOut();
    }



    @Override
    public String toString() {
        return "Users{" +
                "users=" + users +
                '}';
    }
}
