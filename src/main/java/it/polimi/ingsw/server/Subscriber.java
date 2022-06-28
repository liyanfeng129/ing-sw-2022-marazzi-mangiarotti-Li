package it.polimi.ingsw.server;

import java.io.Serializable;

public class Subscriber implements Serializable {
    private String userName;
    private String IpAddress;
    private int portNumber;
    private int countToTimeOut = 0;

    public int getCountToTimeOut() {
        return countToTimeOut;
    }

    public void setCountToTimeOut(int countToTimeOut) {
        this.countToTimeOut = countToTimeOut;
    }

    public Subscriber(String userName, String ipAddress, int portNumber) {
        this.userName = userName;
        IpAddress = ipAddress;
        this.portNumber = portNumber;
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
