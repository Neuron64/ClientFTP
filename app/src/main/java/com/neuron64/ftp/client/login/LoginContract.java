package com.neuron64.ftp.client.login;

import com.neuron64.ftp.client.BasePresenter;
import com.neuron64.ftp.client.BaseView;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

/**
 * Created by Neuron on 02.09.2017.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showConnection(List<UserConnection> connections);

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showError();
    }

    interface Presenter extends BasePresenter{

    }
}
