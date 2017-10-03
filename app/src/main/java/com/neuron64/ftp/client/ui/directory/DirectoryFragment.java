package com.neuron64.ftp.client.ui.directory;

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
import com.neuron64.ftp.client.util.Preconditions;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryFragment extends BaseFragment implements DirectoryContact.View{

    public static final String TAG = "DirectoryFragment";

    private DirectoryContact.Presenter presenter;

    public static DirectoryFragment newInstance() {
        Bundle args = new Bundle();

        DirectoryFragment fragment = new DirectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void attachPresenter(@NonNull DirectoryContact.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
        this.presenter.attachView(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.directory_fragment;
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
        if(presenter != null){
            presenter.unsubscribe();
        }
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build()
                .inject(this);
    }
}
