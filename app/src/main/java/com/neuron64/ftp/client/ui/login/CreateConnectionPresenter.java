package com.neuron64.ftp.client.ui.login;

import android.support.annotation.NonNull;

import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.UserConnection;

import javax.inject.Inject;

/**
 * Created by Neuron on 17.09.2017.
 */

public class CreateConnectionPresenter implements CreateConnectionContract.Presenter{

    private CreateConnectionContract.View view;

    @Inject
    public CreateConnectionPresenter(){

    }

    @Override
    public void attachView(@NonNull CreateConnectionContract.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onClickCreate() {
        if(view.onValidate()){
            UserConnection connection = view.pickConnection();
        }
    }
}
