package com.neuron64.ftp.client.di.component;

import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.client.ui.login.CreateConnectionFragment;
import com.neuron64.ftp.client.ui.login.LoginFragment;

import dagger.Component;

/**
 * Created by Neuron on 17.09.2017.
 */

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = PresenterModule.class)
public interface ViewComponent {
    void inject(LoginFragment fragment);
    void inject(CreateConnectionFragment fragment);
}
