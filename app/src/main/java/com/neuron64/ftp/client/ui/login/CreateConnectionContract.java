package com.neuron64.ftp.client.ui.login;

import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.domain.model.UserConnection;

/**
 * Created by Neuron on 17.09.2017.
 */

public interface CreateConnectionContract {

    interface View extends BaseView<Presenter>{

         void fillingFields(UserConnection userConnection);

         UserConnection pickConnection();

         boolean onValidate();
    }

    interface Presenter extends BasePresenter<View>{

        void onClickCreate();

    }
}
