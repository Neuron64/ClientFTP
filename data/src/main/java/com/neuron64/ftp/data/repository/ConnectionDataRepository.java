package com.neuron64.ftp.data.repository;

import java.util.Date;
import java.util.List;

import com.neuron64.ftp.data.database.RealmService;
import com.neuron64.ftp.data.mapper.ConnectionMapper;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.model.UserConnection;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;

/**
 * Created by Neuron on 03.09.2017.
 */

public class ConnectionDataRepository implements ConnectionRepository {

    @Override
    public Single<List<UserConnection>> getAllConnection() {
        return RealmService.getAllUserConnection()
                .flatMap(Observable::fromIterable)
                .map(new ConnectionMapper().map)
                .toList();
    }

    @Override
    public Single<UserConnection> saveConnection(String id, String title, String host, String username, String password, String port, Date date) {
        com.neuron64.ftp.data.model.local.UserConnection userConnection = new com.neuron64.ftp.data.model.local.UserConnection(id, title, host, username, password, port);
        RealmService.saveConnection(userConnection);
        return Single.just(userConnection).map(new ConnectionMapper().map);
    }
}
