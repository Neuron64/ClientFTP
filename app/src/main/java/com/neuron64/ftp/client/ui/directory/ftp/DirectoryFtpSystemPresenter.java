package com.neuron64.ftp.client.ui.directory.ftp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.FtpConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetFtpDirectoriesUseCase;
import com.neuron64.ftp.domain.model.UserConnection;

/**
 * Created by yks-11 on 10/17/17.
 */

public class DirectoryFtpSystemPresenter extends DirectoryPresenter<DirectoryFtpContact.View> implements DirectoryFtpContact.Presenter{

    private static final String TAG = "DirectoryFtpSystemPrese";

    private FtpConnectionUseCase ftpConnectionUseCase;

    public DirectoryFtpSystemPresenter(@NonNull RxBus rxBus,
                                       @NonNull GetFtpDirectoriesUseCase getFtpDirectoriesUseCase,
                                       @NonNull FtpConnectionUseCase ftpConnectionUseCase) {
        super(rxBus, getFtpDirectoriesUseCase);
        this.ftpConnectionUseCase = Preconditions.checkNotNull(ftpConnectionUseCase);
    }

    @Override
    public void subscribe() {
        UserConnection userConnection = view.getExtraUserConnection();
        ftpConnectionUseCase.execute(super::subscribe, throwable -> {
            //TODO:Check throwable in connectExecute
            Log.e(TAG, "subscribe: ", throwable);
            view.showLoadingIndicator();
            view.showError();
        }, disposable1 -> view.showLoadingIndicator(), userConnection);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        ftpConnectionUseCase.dispose();
//        ftpConnectionUseCase.disconnectExecute(() -> {/*Empty*/},
//                throwable -> Log.e(TAG, "unsubscribe: ", throwable));
    }

    @Override
    protected String getSimpleNameClass() {
        return this.getClass().getSimpleName();
    }
}
