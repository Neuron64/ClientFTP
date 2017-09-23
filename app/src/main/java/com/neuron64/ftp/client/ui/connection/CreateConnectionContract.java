package com.neuron64.ftp.client.ui.connection;


import android.support.annotation.StringRes;

import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.domain.model.UserConnection;

/**
 * Created by Neuron on 17.09.2017.
 */

public interface CreateConnectionContract {

    interface View extends BaseView<Presenter>{

         void fillingFields(String userName, String password, String host, String title, String port);

         void pickConnection();

         boolean onValidate();

         void updateSubmitButtonViewState(boolean enabled);

         void showSnackBar(@StringRes int message);

        UserConnection currentArgConnection();
    }

    interface Presenter extends BasePresenter<View>{

        void onClickCreate();

        void sendConnection(String userName, String password, String host, String title, String port);

    }
}
