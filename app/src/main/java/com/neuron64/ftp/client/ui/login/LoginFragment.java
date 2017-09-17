package com.neuron64.ftp.client.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerViewComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.util.ViewMessage;
import com.neuron64.ftp.domain.model.UserConnection;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View{

    private static final String TAG = "LoginFragment";

    @BindView(R.id.rv_main) RecyclerView rvMain;
    @BindView(R.id.ll_root) LinearLayout llRoot;
    @BindView(R.id.ll_progress_bar) LinearLayout llProgressBar;
    @BindView(R.id.bn_create_connection) Button bnCreateConnection;
    @BindView(R.id.ll_empty_list) LinearLayout llEmptyList;

    private ConnectionAdapter connectionAdapter;

    private LoginContract.Presenter presenter;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.connectionAdapter = new ConnectionAdapter(getContext(), eventBus);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rvMain.setAdapter(connectionAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
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
    public void showConnection(@NonNull List<UserConnection> connections) {
        connectionAdapter.setItems(connections);
    }

    @Override
    public void showEmptyList() {
        llEmptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingIndicator() {
        llProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        llProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSnackBar(@StringRes int id) {
        ViewMessage.initSnackBarShort(llRoot, id);
    }

    @OnClick(R.id.bn_create_connection)
    void onClickCreateConnection(){
        presenter.createConnection();
    }
}
