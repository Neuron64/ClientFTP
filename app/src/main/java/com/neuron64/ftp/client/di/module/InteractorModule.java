package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.data.repository.FileSystemDataRepository;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.interactor.CheckConnectionFtpUseCase;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.DeleteConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetAllConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;
import com.neuron64.ftp.domain.interactor.GetFtpDirectoriesUseCase;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.repository.FtpRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class InteractorModule {

    @Singleton @Provides
    GetAllConnectionUseCase getAllConnection(BaseSchedulerProvider schedulerProvider, ConnectionRepository connectionRepository){
        return new GetAllConnectionUseCase(schedulerProvider, connectionRepository);
    }

    @Singleton @Provides
    CreateConnectionUserCase saveConnectionInteractor(BaseSchedulerProvider schedulerProvider, ConnectionRepository connectionRepository){
        return new CreateConnectionUserCase(schedulerProvider, connectionRepository);
    }

    @Singleton @Provides
    CheckConnectionFtpUseCase checkConnectionFtp(BaseSchedulerProvider schedulerProvider, FtpRepository ftpRepository){
        return new CheckConnectionFtpUseCase(schedulerProvider, ftpRepository);
    }

    @Singleton @Provides
    DeleteConnectionUseCase deleteConnectionUseCase(BaseSchedulerProvider schedulerProvider, ConnectionRepository connectionRepository){
        return new DeleteConnectionUseCase(schedulerProvider, connectionRepository);
    }

    @DirectoryScope @Provides
    GetDirectoriesUseCase getDirectoriesUseCase(BaseSchedulerProvider schedulerProvider, FileSystemDataRepository fileSystemRepository){
        return new GetDirectoriesUseCase(fileSystemRepository, schedulerProvider);
    }

    @DirectoryScope @Provides
    GetFtpDirectoriesUseCase getFtpDirectoriesUseCase(BaseSchedulerProvider schedulerProvider, FtpRepository ftpRepository){
        return new GetFtpDirectoriesUseCase(ftpRepository, schedulerProvider);
    }
}
