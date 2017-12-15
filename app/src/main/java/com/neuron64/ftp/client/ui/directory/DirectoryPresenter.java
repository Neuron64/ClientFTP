package com.neuron64.ftp.client.ui.directory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.NavigateEvent;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.interactor.CompletableUseCase;
import com.neuron64.ftp.domain.interactor.DirectoryUseCase;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.params.MoveFileParams;
import com.neuron64.ftp.domain.params.RenameFileParams;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

/**
 * Created by yks-11 on 10/17/17.
 */

public abstract class DirectoryPresenter<V extends DirectoryContact.BaseDirectoryView> implements DirectoryContact.BaseDirectoryPresenter<V>{

    private static final String TAG = "DirectoryFileSystemPresenter";

    protected abstract String getSimpleNameClass();

    /**
     * Check connection before start {@link #getRootDirectory()}
     *
     * @param complete
     */
    protected abstract void checkConnection(Action complete);

    protected V view;

    protected CompositeDisposable disposable;

    @NonNull
    protected RxBus eventBus;

    @NonNull
    protected DirectoryUseCase<String> directoryUseCase;

    @NonNull
    protected CompletableUseCase<RenameFileParams> renameFileUseCase;

    @NonNull
    protected CompletableUseCase<MoveFileParams> moveFileUseCase;

    protected int lvlFolder;

    public DirectoryPresenter(@NonNull RxBus rxBus,
                              @NonNull DirectoryUseCase<String> directoryUseCase,
                              @NonNull CompletableUseCase<RenameFileParams> renameFileUseCase,
                              @NonNull CompletableUseCase<MoveFileParams> moveFileUseCase){
        this.eventBus = Preconditions.checkNotNull(rxBus);
        this.lvlFolder = 0;
        this.directoryUseCase = Preconditions.checkNotNull(directoryUseCase);
        this.renameFileUseCase = Preconditions.checkNotNull(renameFileUseCase);
        this.moveFileUseCase = Preconditions.checkNotNull(moveFileUseCase);
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
        checkConnection(this::getRootDirectory);
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
    public void clickFile(FileInfo file) {
        if(file.isDirectory()) {
            getNextDirectory(file.getDocumentId());
        }else{
            openFileInfoActivity(file);
        }
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

    @Override
    public void removeDocument(FileInfo file) {
        directoryUseCase.executeDeleteDocument(() -> Log.d(TAG, "executeDeleteDocument: "),
                throwable -> Log.e(TAG, "removeDocument: ", throwable),
                disposable -> Log.d(TAG, "removeDocument: disposable"), file.getDocumentId());
}

    @Override
    public void createFile() {
        //TODO: Create File
    }

    @Override
    public void moveFile(FileInfo file) {
        MoveFileParams params = new MoveFileParams(file.getDocumentId(), file.getDocumentId(), "");
        moveFileUseCase.execute(() -> Log.d(TAG, "run: "),
                throwable -> Log.e(TAG, "accept: ", throwable),
                disposable -> Log.d(TAG, "moveFile: disposable"),
                params);
    }

    @Override
    public void renameFile(FileInfo file) {
        RenameFileParams params = new RenameFileParams("newName", file.getDocumentId());
        renameFileUseCase.execute(() -> Log.d(TAG, "run: "),
                throwable -> Log.e(TAG, "accept: ", throwable),
                disposable -> Log.d(TAG, "renameFile: disposable"), params);
    }

    @Override
    public void changeFile(FileInfo file) {
        //TODO: Change File
    }

    private void goToPreviousIfNotRoot(){
        if(lvlFolder > 0) {
            getPreviousDirectory();
        }else{
            view.finishActivity();
        }
    }

    protected void getRootDirectory() {
        directoryUseCase.executeRootDirectory(this::showFiles,
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
                    showFiles(fileSystemDirectories);
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
                    showFiles(fileSystemDirectories);
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

    protected void showFiles(List<FileInfo> fileSystemDirectories){
        if(!fileSystemDirectories.isEmpty()) {
            view.hideEmptyList();
            view.showFiles(fileSystemDirectories);
        }else{
            view.clearRecyclerView();
            view.showEmptyList();
        }
    }

    private void openFileInfoActivity(FileInfo file){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constans.EXTRA_FILE_INFO, file);
        eventBus.send(NavigateEvent.nabigateOpenFileInfo(bundle));
    }
}
