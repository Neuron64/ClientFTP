package com.neuron64.ftp.client.login;

import android.support.annotation.NonNull;

import com.neuron64.ftp.domain.usecase.ConnectionUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */


public class LoginPresenter implements LoginContract.Presenter{

    @NonNull
    private CompositeDisposable subscription;

    @NonNull
    private LoginContract.View loginView;

    @NonNull
    private ConnectionUseCase connectionUseCase;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull ConnectionUseCase connectionUseCase) {
        this.loginView = checkNotNull(loginView);
        this.connectionUseCase = checkNotNull(connectionUseCase);

        this.subscription = new CompositeDisposable();
        this.loginView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        initData();
    }

    @Override
    public void unsubscribe() {
        subscription.clear();
    }

    private void initData(){
        Disposable disposable = connectionUseCase.getAllConnection().observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> loginView.showLoadingIndicator())
                .doAfterTerminate(loginView::hideLoadingIndicator)
                .subscribe(loginView::showConnection,
                        throwable -> loginView.showError());
        subscription.add(disposable);
    }
}
