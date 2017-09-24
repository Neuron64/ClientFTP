package com.neuron64.ftp.data.repository;

import java.util.Date;
import java.util.List;

import com.neuron64.ftp.data.database.IRealmService;
import com.neuron64.ftp.data.database.RealmService;
import com.neuron64.ftp.data.mapper.ConnectionMapper;
import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.model.UserConnection;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Neuron on 03.09.2017.
 */

public class ConnectionDataRepository implements ConnectionRepository {

    private final Mapper<UserConnection, com.neuron64.ftp.data.model.local.UserConnection> connectionMapper;
    private final RealmService realmService;

    @Inject
    public ConnectionDataRepository(Mapper<UserConnection, com.neuron64.ftp.data.model.local.UserConnection> connectionMapper, RealmService realmService){
        this.connectionMapper = connectionMapper;
        this.realmService = realmService;
    }

    @Override
    public Single<List<UserConnection>> getAllConnection() {
        return realmService.getAllUserConnection()
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(connectionMapper::map)
                .toList();
    }

    @Override
    public Single<UserConnection> saveConnection(String id, String title, String host, String username, String password, String port, Date date) {
        com.neuron64.ftp.data.model.local.UserConnection userConnection = new com.neuron64.ftp.data.model.local.UserConnection(id, title, host, username, password, port);
        return realmService.saveConnection(userConnection)
                .andThen(Single.just(userConnection)
                .map(connectionMapper::map));
    }
}
