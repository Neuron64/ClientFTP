package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Neuron on 03.09.2017.
 */

public class GetAllConnectionUseCase extends SingleUseCase<List<UserConnection>, Void>{

    private ConnectionRepository connectionRepository;

    @Inject
    public GetAllConnectionUseCase(BaseSchedulerProvider schedulerProvider, ConnectionRepository connectionRepository) {
        super(schedulerProvider);
        this.connectionRepository = connectionRepository;
    }

    @Override
    public Single<List<UserConnection>> buildUseCase(Void aVoid) {
        return connectionRepository.getAllConnection();
    }
}
