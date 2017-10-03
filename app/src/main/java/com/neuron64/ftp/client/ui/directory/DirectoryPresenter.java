package com.neuron64.ftp.client.ui.directory;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.util.Preconditions;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryPresenter implements DirectoryContact.Presenter {

    private DirectoryContact.View view;

    private CompositeDisposable disposable;

    @NonNull
    private RxBus rxBus;

    public DirectoryPresenter(@NonNull RxBus rxBus){
        this.rxBus = Preconditions.checkNotNull(rxBus);
    }

    @Override
    public void attachView(@NonNull DirectoryContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

}
