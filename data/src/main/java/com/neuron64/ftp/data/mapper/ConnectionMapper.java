package com.neuron64.ftp.data.mapper;


import com.neuron64.ftp.domain.model.UserConnection;

import javax.inject.Singleton;


/**
 * Created by Neuron on 03.09.2017.
 */

@Singleton
public class ConnectionMapper extends Mapper<UserConnection, com.neuron64.ftp.data.model.local.UserConnection>{

    @Override
    public UserConnection map(com.neuron64.ftp.data.model.local.UserConnection conn) {
        return new UserConnection(conn.getId(), conn.getNameConnection(), conn.getHost(), conn.getUserName(), conn.getPassword(), conn.getPort());
    }

    @Override
    public com.neuron64.ftp.data.model.local.UserConnection reverseMap(UserConnection conn) {
        return new com.neuron64.ftp.data.model.local.UserConnection(conn.getId(), conn.getNameConnection(), conn.getHost(), conn.getUserName(), conn.getPassword(), conn.getPort());
    }
}