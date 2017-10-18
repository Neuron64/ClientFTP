package com.neuron64.ftp.client.ui.directory.ftp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.interactor.FtpConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetFtpDirectoriesUseCase;
import com.neuron64.ftp.domain.model.UserConnection;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;


/**
 * Created by yks-11 on 10/17/17.
 */

public class DirectoryFtpSystemPresenter extends DirectoryPresenter<DirectoryFtpContact.View> implements DirectoryFtpContact.Presenter{

    private static final String TAG = "DirectoryFtpSystemPrese";

    private GetFtpDirectoriesUseCase getFtpDirectoriesUseCase;
    private FtpConnectionUseCase ftpConnectionUseCase;

    public DirectoryFtpSystemPresenter(@NonNull RxBus rxBus,
                                       @NonNull GetFtpDirectoriesUseCase getFtpDirectoriesUseCase,
                                       @NonNull FtpConnectionUseCase ftpConnectionUseCase) {
        super(rxBus);
        this.getFtpDirectoriesUseCase = Preconditions.checkNotNull(getFtpDirectoriesUseCase);
        this.ftpConnectionUseCase = Preconditions.checkNotNull(ftpConnectionUseCase);
    }

    @Override
    public void subscribe() {
        UserConnection userConnection = view.getExtraUserConnection();
        ftpConnectionUseCase.connectExecute(super::subscribe, throwable -> {
            //TODO:Check throwable in connectExecute
            Log.e(TAG, "subscribe: ", throwable);
        }, userConnection);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        getFtpDirectoriesUseCase.dispose();
        ftpConnectionUseCase.dispose();
//        ftpConnectionUseCase.disconnectExecute(() -> {/*Empty*/},
//                throwable -> Log.e(TAG, "unsubscribe: ", throwable));
    }

    @Override
    protected void getRootDirectory() {
        getFtpDirectoriesUseCase.executeRootDirectory(fileSystemDirectories -> {
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
    protected void getNextDirectory(String directory) {
        getFtpDirectoriesUseCase.executeNextDirectory(fileSystemDirectories -> {
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
    protected void getPreviousDirectory() {
        getFtpDirectoriesUseCase.executePreviousDirectory(fileSystemDirectories -> {
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
