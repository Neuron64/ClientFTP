package com.neuron64.ftp.client.ui.directory.file_system;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.di.component.DaggerDirectoryComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.RecyclerItemClickListener;
import com.neuron64.ftp.client.ui.directory.DirectoryFragment;
import com.neuron64.ftp.client.util.Preconditions;

import javax.inject.Inject;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryFileSystemFragment extends DirectoryFragment<DirectoryFileSystemAdapter, DirectoryFileSystemContact.Presenter> implements DirectoryFileSystemContact.View, RecyclerItemClickListener.OnItemClickListener{

    private static final String TAG = "DirectoryFileSystemFrag";

    public static DirectoryFileSystemFragment newInstance() {
        Bundle args = new Bundle();
        DirectoryFileSystemFragment fragment = new DirectoryFileSystemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.directoryAdapter = new DirectoryFileSystemAdapter(getContext(), presenter.getEventBus());
    }

    @Inject @Override
    public void attachPresenter(@NonNull DirectoryFileSystemContact.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
        this.presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerDirectoryComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build()
                .inject(this);
    }
}
