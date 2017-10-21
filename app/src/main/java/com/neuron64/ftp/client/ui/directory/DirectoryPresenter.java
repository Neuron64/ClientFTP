package com.neuron64.ftp.client.ui.directory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.interactor.DirectoryUseCase;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yks-11 on 10/17/17.
 */

public abstract class DirectoryPresenter<V extends DirectoryContact.BaseDirectoryView> implements DirectoryContact.BaseDirectoryPresenter<V>{

    private static final String TAG = "DirectoryFileSystemPresenter";

    protected abstract String getSimpleNameClass();

    protected V view;

    protected CompositeDisposable disposable;

    @NonNull
    protected RxBus eventBus;

    @NonNull
    protected DirectoryUseCase<String> directoryUseCase;

    protected int lvlFolder;

    public DirectoryPresenter(@NonNull RxBus rxBus, @NonNull DirectoryUseCase<String> directoryUseCase){
        this.eventBus = Preconditions.checkNotNull(rxBus);
        this.lvlFolder = 0;
        this.directoryUseCase = directoryUseCase;
    }

    @Override
    public void attachView(@NonNull V view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(eventBus.asFlowable().subscribe(this::handleEvent));

        initData();
    }

    private void initData(){
        getRootDirectory();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
        directoryUseCase.dispose();
    }

    @Override @NonNull
    public RxBus getEventBus() {
        return eventBus;
    }

    @Override
    public void clickFile(FileSystemDirectory file) {
        getNextDirectory(file.getDocumentId());
    }

    @Override
    public void clickHome() {
        goToPreviousIfNotRoot();
    }

    private void handleEvent(Object event){
        if(event instanceof ButtonEvent){
            ButtonEvent buttonEvent = (ButtonEvent) event;
            switch (buttonEvent.getTypeButtonEvent()){
                case ON_BACK_PRESSED:{
                    if(buttonEvent.getNameClass() != null && getSimpleNameClass().equals(buttonEvent.getNameClass())) {
                        goToPreviousIfNotRoot();
                    }
                    break;
                }
            }
        }
    }

    private void goToPreviousIfNotRoot(){
        if(lvlFolder > 0) {
            getPreviousDirectory();
        }else{
            view.finishActivity();
        }
    }

    protected void getRootDirectory() {
        directoryUseCase.executeRootDirectory(fileSystemDirectories -> {
                    view.showFiles(fileSystemDirectories);
                },
                throwable -> {
                    Log.e(TAG, "subscribe getRootDirectory: throwable", throwable);
                    view.clearRecyclerView();
                    view.showEmptyList();
                    view.showError();
                },
                disposable1 -> { /*Empty*/},
                () -> view.hideLoadingIndicator());
    }

    protected void getNextDirectory(String directory) {
        directoryUseCase.executeNextDirectory(fileSystemDirectories -> {
                    lvlFolder++;
                    view.showFiles(fileSystemDirectories);
                },
                throwable -> {
                    Log.e(TAG, "subscribe getNextDirectory: throwable", throwable);
                    view.clearRecyclerView();
                    view.showEmptyList();
                    view.showError();
                },
                disposable1 -> view.showLoadingIndicator(),
                () -> view.hideLoadingIndicator(),
                directory);
    }

    protected void getPreviousDirectory() {
        directoryUseCase.executePreviousDirectory(fileSystemDirectories -> {
                    lvlFolder--;
                    view.showFiles(fileSystemDirectories);
                },
                throwable -> {
                    if(throwable instanceof ErrorThisIsRootDirectory){
                        view.finishActivity();
                    }else{
                        Log.e(TAG, "subscribe getPreviousDirectory: throwable", throwable);
                        view.clearRecyclerView();
                        view.showEmptyList();
                        view.showError();
                    }
                },
                disposable1 -> view.showLoadingIndicator(),
                () -> view.hideLoadingIndicator());
    }
}
