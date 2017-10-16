package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.data.repository.FtpDataRepository;
import com.neuron64.ftp.domain.repository.FtpRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 24.09.2017.
 */

@Module
public class NetworkModule {

    @DirectoryScope @Provides
    FtpRepository ftpFileSystemDataRepository(FtpClientManager ftpClient){
        return new FtpDataRepository(ftpClient);
    }

}
