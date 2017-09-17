package com.neuron64.ftp.client.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerViewComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View{

    private static final String TAG = "LoginFragment";

    private LoginContract.Presenter presenter;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    @Inject @Override
    public void attachPresenter(@NonNull LoginContract.Presenter presenter) {
        this.presenter = presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter!=null){
            presenter.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
        Log.d(TAG, "setPresenter: ");
    }

    @Override
    public void showConnection(List<UserConnection> connections) {
        Log.d(TAG, "showConnection: " + connections);
    }

    @Override
    public void showLoadingIndicator() {
        Log.d(TAG, "showLoadingIndicator: ");
    }

    @Override
    public void hideLoadingIndicator() {
        Log.d(TAG, "hideLoadingIndicator: ");
    }

    @Override
    public void showError() {
        Log.d(TAG, "showError: ");
    }
}
