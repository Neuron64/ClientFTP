package com.neuron64.ftp.client.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by Neuron on 02.09.2017.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T view);

    void subscribe();

    void unsubscribe();

}
