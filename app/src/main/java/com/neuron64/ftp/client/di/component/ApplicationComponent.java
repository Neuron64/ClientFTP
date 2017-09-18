package com.neuron64.ftp.client.di.component;

import android.content.Context;

import com.neuron64.ftp.client.di.module.ApplicationModule;
import com.neuron64.ftp.client.di.module.DataModule;
import com.neuron64.ftp.client.di.module.InteractorModule;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.GetAllConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Neuron on 17.09.2017.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        PresenterModule.class,
        InteractorModule.class,
        DataModule.class})
public interface ApplicationComponent {
    void inject(BaseFragment fragment);
    void inject(BaseActivity activity);

    Context context();
    RxBus rxBus();
    BaseSchedulerProvider scheduler();
    ConnectionRepository connectionRepository();

    //use case
    GetAllConnection getAllConnection();
    CreateConnectionUserCase createConnectionUserCase();

//    SaveConnectionInteractor saveConnectionInteractor();
}
