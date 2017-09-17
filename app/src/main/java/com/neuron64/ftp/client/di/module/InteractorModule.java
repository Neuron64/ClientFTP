package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.interactor.SaveConnectionInteractor;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.interactor.GetAllConnection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class InteractorModule {

    @Singleton @Provides
    GetAllConnection getAllConnection(BaseSchedulerProvider schedulerProvider, ConnectionRepository connectionRepository){
        return new GetAllConnection(schedulerProvider, connectionRepository);
    }

    @Singleton @Provides
    SaveConnectionInteractor saveConnectionInteractor(ConnectionRepository connectionRepository){
        return new SaveConnectionInteractor(connectionRepository);
    }

}
