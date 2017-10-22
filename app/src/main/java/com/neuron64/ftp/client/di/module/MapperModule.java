package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.data.mapper.ConnectionMapper;
import com.neuron64.ftp.data.mapper.FtpFileMapper;
import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.model.UserConnection;

import org.apache.commons.net.ftp.FTPFile;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 24.09.2017.
 */

@Module
public class MapperModule {

    @Singleton @Provides
    Mapper<UserConnection, com.neuron64.ftp.data.model.local.UserConnection> connectionMapper(){
        return new ConnectionMapper();
    }

    @Singleton @Provides
    Mapper<FileInfo, FTPFile> ftpFileMapper(){
        return new FtpFileMapper();
    }
}
