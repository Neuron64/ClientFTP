package com.neuron64.ftp.client.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.ExposeEvent;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.CreateConnectionUserCase;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Neuron on 17.09.2017.
 */

public class CreateConnectionPresenter implements CreateConnectionContract.Presenter{

    private static final String TAG = "CreateConnectionPresent";

    private CreateConnectionContract.View view;

    @NonNull
    private CreateConnectionUserCase createConnectionUserCase;

    @NonNull
    private RxBus eventBus;

    private CompositeDisposable disposable;

    @Inject
    public CreateConnectionPresenter(CreateConnectionUserCase createConnectionUserCase, RxBus eventBus){
        this.createConnectionUserCase = Preconditions.checkNotNull(createConnectionUserCase);
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
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
        createConnectionUserCase.dispose();
    }

    @Override
    public void onClickCreate() {
        view.pickConnection();
    }

    @Override
    public void sendConnection(String userName, String password, String host, String title, String port) {
        createConnectionUserCase.init(title, host, userName, password, port).execute(
                connections -> eventBus.send(ExposeEvent.exposeShowConnection(null)),
                throwable -> {
                    view.showSnackBar(R.string.error_save_connections);
                    view.updateSubmitButtonViewState(false);
                    Log.e(TAG, "sendConnection: ", throwable);
                },
                disposable1 -> view.updateSubmitButtonViewState(true),
                null);
    }

    private void handleEvent(Object event){
        if(event instanceof ButtonEvent){
            view.pickConnection();
        }
    }
}
