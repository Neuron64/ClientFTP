package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.client.di.scope.FileScope;
import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.connection.ConnectionsContract;
import com.neuron64.ftp.client.ui.connection.ConnectionsPresenter;
import com.neuron64.ftp.client.ui.connection.CreateConnectionContract;
import com.neuron64.ftp.client.ui.connection.CreateConnectionPresenter;
import com.neuron64.ftp.client.ui.directory.file_system.DirectoryFileSystemContact;
import com.neuron64.ftp.client.ui.directory.file_system.DirectoryFileSystemPresenter;
import com.neuron64.ftp.client.ui.directory.ftp.DirectoryFtpContact;
import com.neuron64.ftp.client.ui.directory.ftp.DirectoryFtpSystemPresenter;
import com.neuron64.ftp.client.ui.file_info.FileFragmentContact;
import com.neuron64.ftp.client.ui.file_info.FileFragmentInfoPresenter;
import com.neuron64.ftp.domain.interactor.CheckConnectionFtpUseCase;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.DeleteConnectionUseCase;
import com.neuron64.ftp.domain.interactor.FtpConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetAllConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;
import com.neuron64.ftp.domain.interactor.GetFtpDirectoriesUseCase;
import com.neuron64.ftp.domain.interactor.MoveDocumentUseCase;
import com.neuron64.ftp.domain.interactor.MoveFileSystemUseCase;
import com.neuron64.ftp.domain.interactor.MoveFtpFileUseCase;
import com.neuron64.ftp.domain.interactor.RenameFileSystemUseCase;
import com.neuron64.ftp.domain.interactor.RenameFtpFileUseCase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class PresenterModule {

    @ViewScope @Provides
    ConnectionsContract.Presenter login(GetAllConnectionUseCase connectionUseCase, RxBus eventBus, DeleteConnectionUseCase deleteConnectionUseCase, CheckConnectionFtpUseCase checkConnectionFtpUseCase, CreateConnectionUserCase createConnectionUserCase){
        return new ConnectionsPresenter(connectionUseCase, eventBus, deleteConnectionUseCase, checkConnectionFtpUseCase, createConnectionUserCase);
    }

    @ViewScope @Provides
    CreateConnectionContract.Presenter createConnection(CreateConnectionUserCase createConnectionUserCase, CheckConnectionFtpUseCase checkConnectionFtpUseCase, RxBus rxBus){
        return new CreateConnectionPresenter(createConnectionUserCase, checkConnectionFtpUseCase, rxBus);
    }

    @DirectoryScope @Provides
    DirectoryFileSystemContact.Presenter directoriesFileSystem(RxBus rxBus, GetDirectoriesUseCase getDirectoriesUseCase, RenameFileSystemUseCase renameFileSystemUseCase, MoveFileSystemUseCase moveFileSystemUseCase){
        return new DirectoryFileSystemPresenter(rxBus, getDirectoriesUseCase, renameFileSystemUseCase, moveFileSystemUseCase);
    }

    @DirectoryScope @Provides
    DirectoryFtpContact.Presenter directoriesFtp(RxBus rxBus, GetFtpDirectoriesUseCase getDirectoriesUseCase, FtpConnectionUseCase ftpConnectionUseCase, RenameFtpFileUseCase renameFtpFileUseCase, MoveFtpFileUseCase moveFtpFileUseCase){
        return new DirectoryFtpSystemPresenter(rxBus, getDirectoriesUseCase, ftpConnectionUseCase, renameFtpFileUseCase, moveFtpFileUseCase);
    }

    @FileScope @Provides
    FileFragmentContact.Presenter fileInfo(RxBus rxBus){
        return new FileFragmentInfoPresenter(rxBus);
    }
}
