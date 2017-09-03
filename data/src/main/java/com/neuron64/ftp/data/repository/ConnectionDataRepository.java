package com.neuron64.ftp.data.repository;

import java.util.List;

import com.neuron64.ftp.data.database.RealmService;
import com.neuron64.ftp.data.mapper.ConnectionMapper;
import com.neuron64.ftp.domain.ConnectionRepository;
import com.neuron64.ftp.domain.model.UserConnection;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Neuron on 03.09.2017.
 */

public class ConnectionDataRepository implements ConnectionRepository {

    @Override
    public Single<List<UserConnection>> getAllConnection() {
        return RealmService.getAllUserConnection()
                .flatMap(Observable::fromIterable)
                .map(new ConnectionMapper())
                .toList();
    }
}
