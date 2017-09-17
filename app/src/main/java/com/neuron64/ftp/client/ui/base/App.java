package com.neuron64.ftp.client.ui.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

/**
 * Created by Neuron on 03.09.2017.
 */

public class App extends Application{

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
        Picasso.setSingletonInstance(picasso);

        Realm.init(this);
    }

    @NonNull
    public static App getAppContext() {
        return sInstance;
    }
}
