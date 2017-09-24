package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.data.database.IRealmService;
import com.neuron64.ftp.data.database.RealmService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 24.09.2017.
 */

@Module
public class RealmModule {

    @Provides @Singleton
    public RealmService realmService(){
        return new IRealmService();
    }

}
