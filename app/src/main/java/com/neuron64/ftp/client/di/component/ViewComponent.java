package com.neuron64.ftp.client.di.component;

import com.neuron64.ftp.client.di.module.DataModule;
import com.neuron64.ftp.client.di.module.NetworkModule;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.client.ui.connection.ConnectionsFragment;
import com.neuron64.ftp.client.ui.connection.CreateConnectionFragment;
import com.neuron64.ftp.domain.repository.ConnectionRepository;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by Neuron on 17.09.2017.
 */

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = {PresenterModule.class, DataModule.class})
public interface ViewComponent {

    void inject(ConnectionsFragment fragment);

    void inject(CreateConnectionFragment fragment);

}
