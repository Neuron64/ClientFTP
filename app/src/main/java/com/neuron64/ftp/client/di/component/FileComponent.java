package com.neuron64.ftp.client.di.component;

import com.neuron64.ftp.client.di.module.DataModule;
import com.neuron64.ftp.client.di.module.InteractorModule;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.di.scope.FileScope;
import com.neuron64.ftp.client.ui.file_info.FileActivity;
import com.neuron64.ftp.client.ui.file_info.FileFragmentInfoFragment;

import dagger.Component;

/**
 * Created by Neuron on 22.10.2017.
 */

@FileScope
@Component(dependencies = ApplicationComponent.class, modules = {
        PresenterModule.class,
        InteractorModule.class,
        DataModule.class})
public interface FileComponent {

    void inject(FileActivity activity);

    void inject(FileFragmentInfoFragment fragment);

}
