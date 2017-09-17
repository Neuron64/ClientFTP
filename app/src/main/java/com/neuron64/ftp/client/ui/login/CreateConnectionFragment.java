package com.neuron64.ftp.client.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerViewComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.domain.model.UserConnection;

/**
 * Created by Neuron on 17.09.2017.
 */

public class CreateConnectionFragment extends BaseFragment implements CreateConnectionContract.View{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void bind(View root) {
        super.bind(root);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_create_connection;
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void attachPresenter(@NonNull LoginContract.Presenter presenter) {

    }

    @Override
    public void setPresenter(CreateConnectionContract.Presenter presenter) {

    }

    @Override
    public void fillingFields(UserConnection userConnection) {

    }

    @Override
    public UserConnection pickConnection() {
        return null;
    }

    @Override
    public boolean onValidate() {
        return false;
    }
}
