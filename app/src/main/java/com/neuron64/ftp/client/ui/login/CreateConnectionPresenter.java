package com.neuron64.ftp.client.ui.login;

import android.support.annotation.NonNull;

import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.function.Function;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function5;

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

    }

    @Override
    public void sendConnection() {

    }

    @Override
    public io.reactivex.functions.Function<TextViewAfterTextChangeEvent, Boolean> isTitleValue() {
        return null;
    }

    @Override
    public Consumer<Boolean> updateTitleValue() {
        return null;
    }

    @Override
    public io.reactivex.functions.Function<TextViewAfterTextChangeEvent, Boolean> isHostValue() {
        return null;
    }

    @Override
    public Consumer<Boolean> updateHostValue() {
        return null;
    }

    @Override
    public io.reactivex.functions.Function<TextViewAfterTextChangeEvent, Boolean> isPortValue() {
        return null;
    }

    @Override
    public Consumer<Boolean> updatePortValue() {
        return null;
    }

    @Override
    public io.reactivex.functions.Function<TextViewAfterTextChangeEvent, Boolean> isUsernameValue() {
        return null;
    }

    @Override
    public Consumer<Boolean> updateUsernameValue() {
        return null;
    }

    @Override
    public io.reactivex.functions.Function<TextViewAfterTextChangeEvent, Boolean> isPasswordValue() {
        return null;
    }

    @Override
    public Consumer<Boolean> updatePasswordValue() {
        return null;
    }

    @Override
    public Function5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> isFormValid() {
        return null;
    }

}
