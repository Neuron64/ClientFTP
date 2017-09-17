package com.neuron64.ftp.client.di.module;

import android.content.Context;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.executor.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Neuron on 17.09.2017.
 */

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }

    @Singleton @Provides
    Context context(){
        return context;
    }

    @Singleton @Provides
    RxBus rxBus(){
        return new RxBus();
    }

    @Singleton @Provides
    BaseSchedulerProvider baseSchedulerProvider(){
        return new SchedulerProvider();
    }

}
