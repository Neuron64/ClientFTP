package com.neuron64.ftp.client.ui.connection;

import android.os.Bundle;
import android.util.Log;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.FragmentEvent;
import com.neuron64.ftp.client.ui.base.bus.event.NavigateEvent;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.data.exception.ErrorConnectionFtp;
import com.neuron64.ftp.domain.exception.InvalidHostException;
import com.neuron64.ftp.domain.interactor.CheckConnectionFtpUseCase;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;
import com.neuron64.ftp.domain.interactor.DeleteConnectionUseCase;
import com.neuron64.ftp.domain.interactor.GetAllConnectionUseCase;
import com.neuron64.ftp.domain.model.UserConnection;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
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
    private GetAllConnectionUseCase connectionUseCase;

    @NonNull
    private RxBus eventBus;

    private CompositeDisposable disposable;

    @NonNull
    private DeleteConnectionUseCase deleteConnectionUseCase;

    @NonNull
    private CheckConnectionFtpUseCase checkConnectionFtpUseCase;

    @NonNull
    private CreateConnectionUserCase createConnectionUserCase;

    @Inject
    public ConnectionsPresenter(GetAllConnectionUseCase connectionUseCase, RxBus eventBus, DeleteConnectionUseCase deleteConnectionUseCase, CheckConnectionFtpUseCase checkConnectionFtpUseCase, CreateConnectionUserCase createConnectionUserCase) {
        this.connectionUseCase = checkNotNull(connectionUseCase);
        this.eventBus = Preconditions.checkNotNull(eventBus);
        this.deleteConnectionUseCase = Preconditions.checkNotNull(deleteConnectionUseCase);
        this.checkConnectionFtpUseCase = Preconditions.checkNotNull(checkConnectionFtpUseCase);
        this.createConnectionUserCase = Preconditions.checkNotNull(createConnectionUserCase);
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
        deleteConnectionUseCase.execute(
                () -> {
                    UserConnection removedConnection = loginView.removeItemFromAdapter(positionAdapter);
                    loginView.showSnackBarWithAction(R.string.connection_was_delete, R.string.cancel,
                            () -> saveConnection(removedConnection));
                },
                throwable ->{
                    loginView.showSnackBar(R.string.error_connection_delete);
                    Log.e(TAG, "onDeleteConnection: ", throwable);
                }, connection.getId());
    }

    private void saveConnection(UserConnection userConnection){
        createConnectionUserCase.init(userConnection.getId(),
                userConnection.getNameConnection(),
                userConnection.getHost(),
                userConnection.getUserName(),
                userConnection.getPassword(),
                userConnection.getPort())
                .execute(
                        userConnection1 -> loginView.addConnection(userConnection1, true),
                        throwable -> loginView.showSnackBar(R.string.error_connection_cancel_delete),
                        null);
    }

    @Override
    public void onChangeConnection(UserConnection connection, int positionAdapter) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constans.EXTRA_USER_CONNECTION, connection);
        eventBus.send(FragmentEvent.exposeCreateConnection(bundle));
    }

    @Override
    public void onTestConnection(UserConnection connection, int positionAdapter) {
        checkConnectionFtpUseCase.init(connection.getHost(), connection.getUserName(), connection.getPassword(), connection.getPort()).
                execute(() -> loginView.showSnackBar(R.string.success),
                        throwable -> {
                            if(throwable instanceof InvalidHostException){
                                loginView.showSnackBar(R.string.error_need_host);
                            }else if(throwable instanceof ErrorConnectionFtp ||
                                    throwable instanceof IOException){
                                loginView.showSnackBar(R.string.error_connection_connect);
                            }
                            Log.e(TAG, "onTestConnection: ", throwable);
                        }, null);
    }

    @Override
    public void clickOnConnection(UserConnection userConnection) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constans.EXTRA_USER_CONNECTION, userConnection);
        eventBus.send(NavigateEvent.navigateOpenDirectory(bundle));
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
        checkConnectionFtpUseCase.dispose();
        deleteConnectionUseCase.dispose();
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
            eventBus.send(FragmentEvent.exposeCreateConnection(null));
        }
    }

    public interface OnClickListener{
        void onClick();
    }
}
