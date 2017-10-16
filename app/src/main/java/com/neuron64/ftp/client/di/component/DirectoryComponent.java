package com.neuron64.ftp.client.di.component;

import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.client.di.scope.ViewScope;
import com.neuron64.ftp.client.ui.directory.DirectoryFragment;

import dagger.Component;

/**
 * Created by yks-11 on 10/13/17.
 */

@DirectoryScope
@Component(dependencies = ApplicationComponent.class, modules = {PresenterModule.class})
public interface DirectoryComponent {

    void inject(DirectoryFragment fragment);

}
