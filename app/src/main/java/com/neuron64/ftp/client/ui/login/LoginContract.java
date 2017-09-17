package com.neuron64.ftp.client.ui.login;

import android.support.annotation.NonNull;

import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

/**
 * Created by Neuron on 02.09.2017.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void attachPresenter(@NonNull Presenter presenter);

        void showConnection(@NonNull List<UserConnection> connections);

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showError();
    }

    interface Presenter extends BasePresenter{
        void attachView(@NonNull View view);
    }
}
