package com.neuron64.ftp.domain.usecase;

import com.neuron64.ftp.domain.ConnectionRepository;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Neuron on 03.09.2017.
 */

public class ConnectionUseCase {

    private final ConnectionRepository connectionRepository;

    public ConnectionUseCase(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public Single<List<UserConnection>> getAllConnection(){
        return connectionRepository.getAllConnection().subscribeOn(Schedulers.io());
    }
}
