package com.neuron64.ftp.client.ui.connection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import butterknife.OnClick;

import static com.neuron64.ftp.client.util.Preconditions.checkNotNull;

/**
 * Created by Neuron on 02.09.2017.
 */

public class ConnectionsFragment extends BaseFragment implements ConnectionsContract.View, ConnectionsAdapter.OnItemClickListener{

    public static final String TAG = "ConnectionsFragment";

    @BindView(R.id.rv_main) RecyclerView rvMain;
    @BindView(R.id.ll_root) ConstraintLayout llRoot;
    @BindView(R.id.ll_progress_bar) LinearLayout llProgressBar;
    @BindView(R.id.ll_empty_list) LinearLayout llEmptyList;

    private ConnectionsAdapter connectionAdapter;

    private ConnectionsContract.Presenter presenter;

    public static ConnectionsFragment newInstance(){
        return new ConnectionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.connectionAdapter = new ConnectionsAdapter(getContext(), presenter.getRxBus(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        rvMain.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMain.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(connectionAdapter);
        return view;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    @Inject @Override
    public void attachPresenter(@NonNull ConnectionsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
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
        if(presenter != null){
            presenter.subscribe();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void hideEmptyList() {
        llEmptyList.setVisibility(View.GONE);
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

    @Override
    public void onDeleteConnection(UserConnection connection, int positionAdapter) {
        presenter.onDeleteConnection(connection, positionAdapter);
    }

    @Override
    public void onChangeConnection(UserConnection connection, int positionAdapter) {
        presenter.onChangeConnection(connection, positionAdapter);
    }

    @Override
    public void onTestConnection(UserConnection connection, int positionAdapter) {
        presenter.onTestConnection(connection, positionAdapter);
    }
}
