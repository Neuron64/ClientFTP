package com.neuron64.ftp.client.ui.connection;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

/**
 * Created by Neuron on 02.09.2017.
 */

public interface ConnectionsContract {

    interface View extends BaseView<Presenter> {

        void addConnection(@NonNull UserConnection connections, boolean toStart);

        void showConnection(@NonNull List<UserConnection> connections);

        void showEmptyList();

        void hideEmptyList();

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showSnackBar(@StringRes int message);

        void showSnackBarWithAction(@StringRes int message, @StringRes int messageAction, ConnectionsPresenter.OnClickListener onClickListener);

        UserConnection removeItemFromAdapter(int position);
    }

    interface Presenter extends BasePresenter<View>{

        RxBus getRxBus();

        void onDeleteConnection(UserConnection connection, int positionAdapter);

        void onChangeConnection(UserConnection connection, int positionAdapter);

        void onTestConnection(UserConnection connection, int positionAdapter);
    }
}
