package com.neuron64.ftp.client.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */

public class LoginFragment extends Fragment implements LoginContract.View{

    private static final String TAG = "LoginFragment";

    private LoginContract.Presenter presenter;

    private Unbinder unbinder;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
