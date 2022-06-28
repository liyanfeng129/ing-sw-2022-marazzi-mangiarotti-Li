package it.polimi.ingsw.server;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Subscriber implements Serializable {
    private String userName;
    private String IpAddress;
    private int portNumber;
    LocalDateTime lastAccessTime;

    public Subscriber(String userName, String ipAddress, int portNumber) {
        this.userName = userName;
        IpAddress = ipAddress;
        this.portNumber = portNumber;
        lastAccessTime = LocalDateTime.now();
    }

    public void updateLastAccessTime()
    {
        lastAccessTime = LocalDateTime.now();
    }

    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
