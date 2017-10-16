package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.connection.ConnectionsContract;
import com.neuron64.ftp.client.ui.connection.ConnectionsPresenter;
import com.neuron64.ftp.client.ui.connection.CreateConnectionContract;
import com.neuron64.ftp.client.ui.connection.CreateConnectionPresenter;
import com.neuron64.ftp.client.ui.directory.DirectoryContact;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.domain.interactor.CheckConnectionFtpUseCase;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.DeleteConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetAllConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;

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
    DirectoryContact.Presenter showDirectory(RxBus rxBus, GetDirectoriesUseCase getDirectoriesUseCase){
        return new DirectoryPresenter(rxBus, getDirectoriesUseCase);
    }
}
