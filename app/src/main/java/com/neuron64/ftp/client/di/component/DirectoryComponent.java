package com.neuron64.ftp.client.di.component;

import com.neuron64.ftp.client.di.module.DataModule;
import com.neuron64.ftp.client.di.module.InteractorModule;
import com.neuron64.ftp.client.di.module.NetworkModule;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.di.scope.DirectoryScope;
import com.neuron64.ftp.client.ui.directory.file_system.DirectoryFileSystemFragment;
import com.neuron64.ftp.client.ui.directory.ftp.DirectoryFtpFragment;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.repository.FtpRepository;

import dagger.Component;

/**
 * Created by yks-11 on 10/16/17.
 */

@DirectoryScope
@Component(dependencies = ApplicationComponent.class, modules = {
        PresenterModule.class,
        InteractorModule.class,
        NetworkModule.class,
        DataModule.class})
public interface DirectoryComponent {

    void inject(DirectoryFileSystemFragment fragment);

    void inject(DirectoryFtpFragment fragment);

    FtpRepository connectionRepository();

}
