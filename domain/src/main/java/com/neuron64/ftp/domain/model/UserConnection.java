package com.neuron64.ftp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Neuron on 03.09.2017.
 */

public class UserConnection implements Parcelable{

    private String id;

    private String nameConnection;

    private String host;

    private String userName;

    private String password;

    private Integer port;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nameConnection);
        dest.writeString(this.host);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeValue(this.port);
    }

    protected UserConnection(Parcel in) {
        this.id = in.readString();
        this.nameConnection = in.readString();
        this.host = in.readString();
        this.userName = in.readString();
        this.password = in.readString();
        this.port = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<UserConnection> CREATOR = new Creator<UserConnection>() {
        @Override
        public UserConnection createFromParcel(Parcel source) {
            return new UserConnection(source);
        }

        @Override
        public UserConnection[] newArray(int size) {
            return new UserConnection[size];
        }
    };
}
