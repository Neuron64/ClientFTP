package com.neuron64.ftp.client.di.component;

import android.content.Context;

import com.neuron64.ftp.client.di.module.ApplicationModule;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Neuron on 17.09.2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context context();
    RxBus rxBus();
    BaseSchedulerProvider scheduler();
}
