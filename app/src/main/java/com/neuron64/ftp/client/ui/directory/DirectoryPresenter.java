package com.neuron64.ftp.client.ui.directory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryPresenter implements DirectoryContact.Presenter {

    private static final String TAG = "DirectoryPresenter";

    private DirectoryContact.View view;

    private CompositeDisposable disposable;

    private GetDirectoriesUseCase getDirectoriesUseCase;

    @NonNull
    private RxBus rxBus;

    public DirectoryPresenter(@NonNull RxBus rxBus, @NonNull GetDirectoriesUseCase getDirectoriesUseCase){
        this.rxBus = Preconditions.checkNotNull(rxBus);
        this.getDirectoriesUseCase = Preconditions.checkNotNull(getDirectoriesUseCase);
    }

    @Override
    public void attachView(@NonNull DirectoryContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        getDirectoriesUseCase.execute(fileSystemDirectories -> {
            Log.d(TAG, "subscribe: " + fileSystemDirectories.toString());
        }, throwable -> {
            Log.e(TAG, "subscribe: throwable", throwable);
        }, accept -> {
            Log.d(TAG, "subscribe: accept");
        }, () -> {
            Log.d(TAG, "subscribe: run ");
        }, null);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

}
