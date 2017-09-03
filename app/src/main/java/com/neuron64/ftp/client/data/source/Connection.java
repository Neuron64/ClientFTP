//package com.neuron64.ftp.client.data.source;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import io.realm.RealmObject;
//
///**
// * Created by Neuron on 03.09.2017.
// */
//
//public class Connection extends RealmObject {
//
//    @NonNull
//    private int id;
//
//    @NonNull
//    private String nameConnection;
//
//    @NonNull
//    private String host;
//
//    @NonNull
//    private String userName;
//
//    @NonNull
//    private String password;
//
//    @Nullable
//    private String port;
//
//    public Connection(@NonNull String nameConnection, @NonNull String host, @NonNull String userName, @NonNull String password, @Nullable String port) {
//        this.nameConnection = nameConnection;
//        this.host = host;
//        this.userName = userName;
//        this.password = password;
//        this.port = port;
//    }
//
//    @NonNull
//    public String getNameConnection() {
//        return nameConnection;
//    }
//
//    public void setNameConnection(@NonNull String nameConnection) {
//        this.nameConnection = nameConnection;
//    }
//
//    @NonNull
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(@NonNull String host) {
//        this.host = host;
//    }
//
//    @NonNull
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(@NonNull String userName) {
//        this.userName = userName;
//    }
//
//    @NonNull
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(@NonNull String password) {
//        this.password = password;
//    }
//
//    @Nullable
//    public String getPort() {
//        return port;
//    }
//
//    public void setPort(@Nullable String port) {
//        this.port = port;
//    }
//}
