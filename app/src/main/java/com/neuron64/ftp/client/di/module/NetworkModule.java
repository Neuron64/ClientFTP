package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.data.repository.FtpDataRepository;
import com.neuron64.ftp.domain.repository.FtpRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 24.09.2017.
 */

@Module
public class NetworkModule {

    @Singleton @Provides
    FtpRepository ftpRepository(){
        return new FtpDataRepository();
    }

}
