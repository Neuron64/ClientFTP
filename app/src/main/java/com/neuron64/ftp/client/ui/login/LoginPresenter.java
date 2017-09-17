package com.neuron64.ftp.client.ui.login;

import android.support.annotation.NonNull;

import com.neuron64.ftp.domain.interactor.GetAllConnection;

import javax.inject.Inject;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */


public class LoginPresenter implements LoginContract.Presenter{

    @NonNull
    private LoginContract.View loginView;

    @NonNull
    private GetAllConnection connectionUseCase;

    @Inject
    public LoginPresenter(@NonNull GetAllConnection connectionUseCase) {
        this.connectionUseCase = checkNotNull(connectionUseCase);
    }

    @Override
    public void attachView(@NonNull LoginContract.View loginView){
        this.loginView = checkNotNull(loginView);
    }

    @Override
    public void subscribe() {
        initData();
    }

    @Override
    public void unsubscribe() {
        connectionUseCase.dispose();
    }

    private void initData(){
        connectionUseCase.execute(connections -> loginView.showConnection(connections),
                throwable -> loginView.showError(),
                disposable1 -> loginView.showLoadingIndicator(),
                () -> loginView.hideLoadingIndicator(),
                null);
    }
}
