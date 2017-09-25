package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.repository.ConnectionRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;

/**
 * Created by Neuron on 25.09.2017.
 */

public class DeleteConnectionUseCase extends CompletableUseCase<String> {

    @NonNull
    private ConnectionRepository connectionRepository;

    @Inject
    public DeleteConnectionUseCase(BaseSchedulerProvider schedulerProvider, ConnectionRepository connectionRepository) {
        super(schedulerProvider);
        this.connectionRepository = connectionRepository;
    }

    @Override
    public Completable buildCompletable(String id) {
        return connectionRepository.deleteConnection(id);
    }
}
