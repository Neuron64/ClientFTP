package com.neuron64.ftp.client.ui.connection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.ExposeEvent;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.GetAllConnection;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */


public class ConnectionsPresenter implements ConnectionsContract.Presenter{

    private static final String TAG = "ConnectionsPresenter";

    @NonNull
    private ConnectionsContract.View loginView;

    @NonNull
    private GetAllConnection connectionUseCase;

    @NonNull
    private RxBus eventBus;

    private CompositeDisposable disposable;

    @Inject
    public ConnectionsPresenter(@NonNull GetAllConnection connectionUseCase, RxBus eventBus) {
        this.connectionUseCase = checkNotNull(connectionUseCase);
        this.eventBus = Preconditions.checkNotNull(eventBus);
    }

    @Override
    public void attachView(@NonNull ConnectionsContract.View loginView){
        this.loginView = checkNotNull(loginView);
    }

    @Override
    public RxBus getRxBus() {
        return eventBus;
    }

    @Override
    public void onDeleteConnection(UserConnection connection, int positionAdapter) {
        //TODO: onDeleteConnection
    }

    @Override
    public void onChangeConnection(UserConnection connection, int positionAdapter) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constans.EXTRA_USER_CONNECTION, connection);
        eventBus.send(ExposeEvent.exposeCreateConnection(bundle));
    }

    @Override
    public void onTestConnection(UserConnection connection, int positionAdapter) {
        //TODO: onTestConnection
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(eventBus.asFlowable().subscribe(this::handleEvent));

        initData();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
        connectionUseCase.dispose();
    }

    private void initData(){
        connectionUseCase.execute(this::showConnections,
                throwable -> {
                    loginView.showSnackBar(R.string.error_get_user_connections);
                    Log.e(TAG, "initData: ", throwable);
                },
                disposable1 -> loginView.showLoadingIndicator(),
                () -> loginView.hideLoadingIndicator(),
                null);
    }

    private void showConnections(List<UserConnection> userConnections){
        if(userConnections.isEmpty()) {
            loginView.showEmptyList();
        } else {
            loginView.hideEmptyList();
            loginView.showConnection(userConnections);
        }
    }

    private void handleEvent(Object event){
        if(event instanceof ButtonEvent){
            eventBus.send(ExposeEvent.exposeCreateConnection(null));
        }
    }
}
