package com.neuron64.ftp.data.mapper;


import com.neuron64.ftp.domain.model.UserConnection;

import io.reactivex.functions.Function;


/**
 * Created by Neuron on 03.09.2017.
 */

public class ConnectionMapper {

    public  Function<com.neuron64.ftp.data.model.local.UserConnection, UserConnection> map = conn ->
            new UserConnection(conn.getId(), conn.getNameConnection(), conn.getHost(), conn.getUserName(), conn.getPassword(), conn.getPort());

    public Function<UserConnection, com.neuron64.ftp.data.model.local.UserConnection> reverseMap = conn ->
            new com.neuron64.ftp.data.model.local.UserConnection(conn.getId(), conn.getNameConnection(), conn.getHost(), conn.getUserName(), conn.getPassword(), conn.getPort());
}