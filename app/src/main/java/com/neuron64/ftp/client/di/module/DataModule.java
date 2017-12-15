package com.neuron64.ftp.client.di.module;

import android.content.Context;

import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.data.database.RealmService;
import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.data.repository.ConnectionDataRepository;
import com.neuron64.ftp.data.repository.FileSystemDataRepository;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class DataModule {

    @ViewScope @Provides
    ConnectionRepository connectionRepository(Mapper<UserConnection, com.neuron64.ftp.data.model.local.UserConnection> connectionMapper, RealmService realmService){
        return new ConnectionDataRepository(connectionMapper, realmService);
    }

    @DirectoryScope @Provides
    FileSystemRepository fileSystemRepository(Context context){
        return new FileSystemDataRepository(context);
    }
}
