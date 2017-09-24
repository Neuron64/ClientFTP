package com.neuron64.ftp.client.ui.connection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.ExposeEvent;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorConnectionFtp;
import com.neuron64.ftp.domain.exception.InvalidHostException;
import com.neuron64.ftp.domain.interactor.CheckConnectionFtpUseCase;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.model.UserConnection;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Neuron on 17.09.2017.
 */

public class CreateConnectionPresenter implements CreateConnectionContract.Presenter{

    private static final String TAG = "CreateConnectionPresent";

    private CreateConnectionContract.View view;

    @NonNull
    private CreateConnectionUserCase createConnectionUserCase;

    @NonNull
    private CheckConnectionFtpUseCase checkConnectionFtpUseCase;

    @NonNull
    private RxBus eventBus;

    private CompositeDisposable disposable;

    @Inject
    public CreateConnectionPresenter(CreateConnectionUserCase createConnectionUserCase, CheckConnectionFtpUseCase checkConnectionFtpUseCase, RxBus eventBus){
        this.createConnectionUserCase = Preconditions.checkNotNull(createConnectionUserCase);
        this.checkConnectionFtpUseCase = Preconditions.checkNotNull(checkConnectionFtpUseCase);
        this.eventBus = Preconditions.checkNotNull(eventBus);
    }

    @Override
    public void attachView(@NonNull CreateConnectionContract.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(eventBus.asFlowable().subscribe(this::handleEvent));

        initPresenter();
    }

    private void initPresenter(){
        UserConnection connection = view.currentArgConnection();
        if(connection != null){
            view.fillingFields(connection.getUserName(), connection.getPassword(), connection.getHost(), connection.getNameConnection(), connection.getPort());
        }else{
            view.fillingFields(null, null, null, null, null);
        }
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
        createConnectionUserCase.dispose();
    }

    @Override
    public void onClickCheckConnection() {
        view.pickConnection(true);
    }

    @Override
    public void sendConnection(String userName, String password, String host, String title, String port) {
        view.updateSubmitButtonViewState(false);
        createConnectionUserCase.init(title, host, userName, password, port).execute(
                connections -> eventBus.send(ExposeEvent.exposeShowConnection(null)),
                throwable -> {
                    if(throwable instanceof InvalidHostException){
                        view.showSnackBar(R.string.error_need_host);
                    }else {
                        view.showSnackBar(R.string.error_save_connections);
                    }
                    view.updateSubmitButtonViewState(true);
                    Log.e(TAG, "sendConnection: ", throwable);
                },
                disposable1 -> view.updateSubmitButtonViewState(false),
                null);
    }

    @Override
    public void sendConnection(String userName, String password, String host, String port) {
        checkConnectionFtpUseCase.init(host, userName, password, port).
                execute(() -> view.showSnackBar(R.string.success),
                        throwable -> {
                            if(throwable instanceof InvalidHostException){
                                view.showSnackBar(R.string.error_need_host);
                            }else if(throwable instanceof ErrorConnectionFtp ||
                                    throwable instanceof IOException){
                                view.showSnackBar(R.string.error_connection_connect);
                            }
                }, null);
    }

    private void handleEvent(Object event){
        if(event instanceof ButtonEvent){
            view.pickConnection(false);
        }
    }
}
