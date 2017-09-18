package com.neuron64.ftp.domain.model;

/**
 * Created by Neuron on 03.09.2017.
 */

public class UserConnection {

    private String id;

    private String nameConnection;

    private String host;

    private String userName;

    private String password;

    private String port;

    public UserConnection(String id, String nameConnection, String host, String userName, String password, String port) {
        this.id = id;
        this.nameConnection = nameConnection;
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameConnection() {
        return nameConnection;
    }

    public void setNameConnection(String nameConnection) {
        this.nameConnection = nameConnection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


}
