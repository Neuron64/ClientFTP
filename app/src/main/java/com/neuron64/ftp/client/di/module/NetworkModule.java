package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.data.repository.FtpDataRepository;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FtpRepository;

import org.apache.commons.net.ftp.FTPFile;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 24.09.2017.
 */

@Module
public class NetworkModule {

    @Singleton @Provides
    FtpRepository ftpFileSystemDataRepository(FtpClientManager ftpClient, Mapper<FileSystemDirectory, FTPFile> ftpFileMapper){
        return new FtpDataRepository(ftpClient, ftpFileMapper);
    }

}
