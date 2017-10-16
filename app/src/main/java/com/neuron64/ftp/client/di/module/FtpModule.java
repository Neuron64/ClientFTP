package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.data.database.IRealmService;
import com.neuron64.ftp.data.database.RealmService;
import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.data.network.IFtpClientManager;

import javax.inject.Singleton;

import dagger.Provides;

/**
 * Created by yks-11 on 10/16/17.
 */

public class FtpModule {

    @DirectoryScope
    @Singleton
    public FtpClientManager realmService(){
        return new IFtpClientManager();
    }

}
