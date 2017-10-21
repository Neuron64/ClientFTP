package com.neuron64.ftp.data.model.local;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by Neuron on 03.09.2017.
 */

@RealmClass
public class UserConnection implements RealmModel {

    public static String FIELD_ID = "id";

    @PrimaryKey
    @Required
    private String id;

    private String nameConnection;

    private String host;

    private String userName;

    private String password;

    private Integer port;

    public UserConnection() {
        //Empty for Realm
    }

    public UserConnection(String id, String nameConnection, String host, String userName, String password, Integer port) {
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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
