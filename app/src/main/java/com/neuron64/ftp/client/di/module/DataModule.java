package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.data.repository.ConnectionDataRepository;
import com.neuron64.ftp.domain.repository.ConnectionRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class DataModule {

    @Singleton
    @Provides
    ConnectionRepository connectionRepository(){
        return new ConnectionDataRepository();
    }

}
