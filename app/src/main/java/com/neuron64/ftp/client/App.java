package com.neuron64.ftp.client;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.neuron64.ftp.client.di.component.ApplicationComponent;
import com.neuron64.ftp.client.di.component.DaggerApplicationComponent;
import com.neuron64.ftp.client.di.module.ApplicationModule;
import com.neuron64.ftp.client.di.module.DataModule;
import com.neuron64.ftp.client.di.module.InteractorModule;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Neuron on 03.09.2017.
 */

public class App extends Application{

    private static App sInstance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
        Picasso.setSingletonInstance(picasso);

        Realm.init(this);

        initializationAppComponent();
    }

    public void initializationAppComponent(){
        if(applicationComponent == null){
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .dataModule(new DataModule())
                    .interactorModule(new InteractorModule())
                    .build();
        }
    }

    @NonNull
    public static App getAppInstance() {
        return sInstance;
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }
}
