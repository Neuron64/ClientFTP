package com.neuron64.ftp.client.ui.directory.ftp;

import android.support.annotation.NonNull;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorConnectionFtp;
import com.neuron64.ftp.domain.interactor.FtpConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetFtpDirectoriesUseCase;
import com.neuron64.ftp.domain.interactor.MoveDocumentUseCase;
import com.neuron64.ftp.domain.interactor.RenameDocumentUseCase;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.params.MoveFileParams;
import com.neuron64.ftp.domain.params.RenameFileParams;

import io.reactivex.functions.Action;

/**
 * Created by yks-11 on 10/17/17.
 */

public class DirectoryFtpSystemPresenter extends DirectoryPresenter<DirectoryFtpContact.View> implements DirectoryFtpContact.Presenter{

    private static final String TAG = "DirectoryFtpSystemPrese";

    private FtpConnectionUseCase ftpConnectionUseCase;

    public DirectoryFtpSystemPresenter(@NonNull RxBus rxBus,
                                       @NonNull GetFtpDirectoriesUseCase getFtpDirectoriesUseCase,
                                       @NonNull FtpConnectionUseCase ftpConnectionUseCase,
                                       @NonNull RenameDocumentUseCase<RenameFileParams> renameFileUseCase,
                                       @NonNull MoveDocumentUseCase<MoveFileParams> moveDocumentUseCase){
        super(rxBus, getFtpDirectoriesUseCase, renameFileUseCase, moveDocumentUseCase);
        this.ftpConnectionUseCase = Preconditions.checkNotNull(ftpConnectionUseCase);
    }

    @Override
    public void subscribe() {
       super.subscribe();
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        ftpConnectionUseCase.dispose();
    }

    @Override
    protected String getSimpleNameClass() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void checkConnection(Action complete) {
        UserConnection userConnection = view.getExtraUserConnection();
        ftpConnectionUseCase.execute(complete, throwable -> {
            if(throwable instanceof ErrorConnectionFtp){
                view.showMessage(R.string.error_connection_connect);
            }
            view.hideLoadingIndicator();
            view.showError();
        }, disposable1 -> view.showLoadingIndicator(), userConnection);
    }

    @Override
    public void downloadFile(FileInfo fileInfo) {
        //TODO: downloadFile
    }
}
