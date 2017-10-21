package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.data.network.IFtpClientManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yks-11 on 10/16/17.
 */

@Module
public class FtpModule {

    @Singleton @Provides
    public FtpClientManager ftpClientManager(){
        return new IFtpClientManager();
    }

}
