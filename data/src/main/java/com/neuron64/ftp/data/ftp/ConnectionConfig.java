package com.neuron64.ftp.data.ftp;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yks-11 on 10/18/17.
 */

public class ConnectionConfig {

    private List<String> directoryIer;

    private String id;

    private String nameConnection;

    private String host;

    private String userName;

    private String password;

    private Integer port;

    public ConnectionConfig() {
        this.directoryIer = new ArrayList<>();
        directoryIer.add("");
    }

    public ConnectionConfig initConfig(String id, String nameConnection, String host, String userName, String password, Integer port){
        this.id = id;
        this.nameConnection = nameConnection;
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.port = port;

        return this;
    }

    public ConnectionConfig addDirectory(String derectory){
        directoryIer.add(derectory);

        return this;
    }

    public ConnectionConfig backDirectory(){
        if(!directoryIer.isEmpty()){
            directoryIer.remove(directoryIer.size() - 1);
        }

        return this;
    }

    /**
     * Root directory have empty path - "",
     * another's directories path - "first/second/"...
    **/
    public String getFullPath(){
        if(directoryIer.size() == 1)
            return directoryIer.get(0);

        StringBuilder stringBuilder = new StringBuilder();
        for (String directory : directoryIer) {
            stringBuilder.append(directory).append("/");
        }
        return stringBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public String getNameConnection() {
        return nameConnection;
    }

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPort() {
        return port;
    }
}
