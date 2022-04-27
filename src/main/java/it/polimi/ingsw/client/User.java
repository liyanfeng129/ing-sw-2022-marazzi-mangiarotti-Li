package it.polimi.ingsw.client;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private Boolean isLogged;

    public User(String name, boolean isLogged) {
        this.name = name;
        this.isLogged = isLogged;
    }

    public String getName() {
        return name;
    }

    public Boolean isLogged() {
        return isLogged;
    }

    public void logging() { isLogged = true;}

    public void logOut() { isLogged = false;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", isLogged=" + isLogged +
                '}';
    }
}
