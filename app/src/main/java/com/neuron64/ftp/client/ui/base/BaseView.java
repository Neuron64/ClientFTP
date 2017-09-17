package com.neuron64.ftp.client.ui.base;

import android.support.annotation.NonNull;

import com.neuron64.ftp.client.ui.login.LoginContract;

/**
 * Created by Neuron on 02.09.2017.
 */

public interface BaseView<T> {

    void attachPresenter(@NonNull LoginContract.Presenter presenter);

    void setPresenter(T presenter);

}
