package com.neuron64.ftp.client.ui.directory.file_system;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryFileSystemPresenter extends DirectoryPresenter<DirectoryFileSystemContact.View> implements DirectoryFileSystemContact.Presenter {

    private static final String TAG = "DirectoryFileSystemPresenter";

    private GetDirectoriesUseCase getDirectoriesUseCase;

    public DirectoryFileSystemPresenter(@NonNull RxBus rxBus, @NonNull GetDirectoriesUseCase getDirectoriesUseCase){
        super(rxBus);
        this.getDirectoriesUseCase = Preconditions.checkNotNull(getDirectoriesUseCase);
    }

    @Override
    public void attachView(@NonNull DirectoryFileSystemContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        getDirectoriesUseCase.dispose();
    }

    @Override
    protected void getRootDirectory(){
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

    @Override
    protected void getNextDirectory(String directory){
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

    @Override
    protected void getPreviousDirectory(){
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

    @Override
    protected String getSimpleNameClass() {
        return this.getClass().getSimpleName();
    }
}
