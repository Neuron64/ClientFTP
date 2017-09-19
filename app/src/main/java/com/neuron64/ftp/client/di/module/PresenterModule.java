package com.neuron64.ftp.client.di.module;

import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.login.CreateConnectionContract;
import com.neuron64.ftp.client.ui.login.CreateConnectionPresenter;
import com.neuron64.ftp.client.ui.login.LoginContract;
import com.neuron64.ftp.client.ui.login.LoginPresenter;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.GetAllConnection;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class PresenterModule {

    @ViewScope @Provides
    LoginContract.Presenter login(GetAllConnection connectionUseCase, RxBus rxBus){
        return new LoginPresenter(connectionUseCase, rxBus);
    }

    @ViewScope @Provides
    CreateConnectionContract.Presenter createConnection(CreateConnectionUserCase createConnectionUserCase, RxBus rxBus){
        return new CreateConnectionPresenter(createConnectionUserCase, rxBus);
    }
}
