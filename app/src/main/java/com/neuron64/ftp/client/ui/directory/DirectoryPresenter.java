package com.neuron64.ftp.client.ui.directory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

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
    private RxBus eventBus;

    private int lvlFolder;

    public DirectoryPresenter(@NonNull RxBus rxBus, @NonNull GetDirectoriesUseCase getDirectoriesUseCase){
        this.eventBus = Preconditions.checkNotNull(rxBus);
        this.getDirectoriesUseCase = Preconditions.checkNotNull(getDirectoriesUseCase);
    }

    @Override
    public void attachView(@NonNull DirectoryContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(eventBus.asFlowable().subscribe(this::handleEvent));

        initData();
    }

    private void initData(){
        lvlFolder = 0;
        getRootDirectory();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
        getDirectoriesUseCase.dispose();
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
                    goToPreviousIfNotRoot();
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

    private void getRootDirectory(){
        getDirectoriesUseCase.executeRootDirectory(fileSystemDirectories -> {
                    lvlFolder = 0;
                    view.showFiles(fileSystemDirectories);
                },
                throwable -> {
                    Log.e(TAG, "subscribe getRootDirectory: throwable", throwable);
                    view.clearRecyclerView();
                    view.showEmptyList();
                    view.showError();
                },
                disposable1 -> view.showLoadingIndicator(),
                () -> view.hideLoadingIndicator());
    }

    private void getNextDirectory(String directory){
        getDirectoriesUseCase.executeNextDirectory(fileSystemDirectories -> {
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

    private void getPreviousDirectory(){
        getDirectoriesUseCase.executePreviousDirectory(fileSystemDirectories -> {
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
