package com.neuron64.ftp.client.di.component;

import android.content.Context;

import com.neuron64.ftp.client.di.module.ApplicationModule;
import com.neuron64.ftp.client.di.module.DataModule;
import com.neuron64.ftp.client.di.module.InteractorModule;
import com.neuron64.ftp.client.di.module.MapperModule;
import com.neuron64.ftp.client.di.module.NetworkModule;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.di.module.RealmModule;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.data.database.IRealmService;
import com.neuron64.ftp.data.database.RealmService;
import com.neuron64.ftp.data.mapper.ConnectionMapper;
import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.DeleteConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetAllConnection;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.repository.FtpRepository;

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
        DataModule.class,
        MapperModule.class,
        RealmModule.class,
        NetworkModule.class})
public interface ApplicationComponent {
    void inject(BaseFragment fragment);
    void inject(BaseActivity activity);

    Context context();
    RxBus rxBus();
    BaseSchedulerProvider scheduler();

    //use case
    GetAllConnection getAllConnection();
    CreateConnectionUserCase createConnectionUserCase();
    DeleteConnectionUseCase deleteConnectionUseCase();

    //data
    ConnectionRepository connectionRepository();
    FtpRepository ftpRepository();
    Mapper<UserConnection, com.neuron64.ftp.data.model.local.UserConnection> mapper();
    RealmService realmService();
}
