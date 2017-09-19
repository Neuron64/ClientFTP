package com.neuron64.ftp.client.ui.login;


import android.support.annotation.StringRes;

import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.domain.model.UserConnection;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function5;

/**
 * Created by Neuron on 17.09.2017.
 */

public interface CreateConnectionContract {

    interface View extends BaseView<Presenter>{

         void fillingFields(UserConnection userConnection);

         void pickConnection();

         boolean onValidate();

         void updateSubmitButtonViewState(boolean enabled);

         void showSnackBar(@StringRes int message);

    }

    interface Presenter extends BasePresenter<View>{

        void onClickCreate();

        void sendConnection(String userName, String password, String host, String title, String port);

//        Function<TextViewAfterTextChangeEvent, Boolean> isTitleValue();
//
//        Consumer<Boolean> updateTitleValue();
//
//        Function<TextViewAfterTextChangeEvent, Boolean> isHostValue();
//
//        Consumer<Boolean> updateHostValue();
//
//        Function<TextViewAfterTextChangeEvent, Boolean> isPortValue();
//
//        Consumer<Boolean> updatePortValue();
//
//        Function<TextViewAfterTextChangeEvent, Boolean> isUsernameValue();
//
//        Consumer<Boolean> updateUsernameValue();
//
//        Function<TextViewAfterTextChangeEvent, Boolean> isPasswordValue();
//
//        Consumer<Boolean> updatePasswordValue();
//
//        Function5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> isFormValid();

    }
}
