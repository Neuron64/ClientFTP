package com.neuron64.ftp.client.ui.directory.ftp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.di.component.DaggerDirectoryComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.directory.DirectoryFragment;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.model.UserConnection;

import javax.inject.Inject;

/**
 * Created by yks-11 on 10/17/17.
 */

public class DirectoryFtpFragment extends DirectoryFragment<DirectoryFtpAdapter, DirectoryFtpContact.Presenter> implements DirectoryFtpContact.View, DirectoryFtpAdapter.OnItemClickListener{

    public static DirectoryFtpFragment newInstance(UserConnection userConnection) {
        Bundle args = new Bundle();
        args.putParcelable(Constans.EXTRA_USER_CONNECTION, userConnection);

        DirectoryFtpFragment fragment = new DirectoryFtpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.directoryAdapter = new DirectoryFtpAdapter(getContext(), presenter.getEventBus(), this);
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull DirectoryFtpContact.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
        this.presenter.attachView(this);
    }

    @Override
    public void onClickDeleteFile(FileInfo fileInfo, int positionAdapter) {
        presenter.removeDocument(fileInfo);
    }

    @Override
    public void onClickItem(FileInfo file, int positionAdapter) {
        presenter.clickFile(file);
    }

    @Override
    public void onClickChangeFile(FileInfo fileInfo, int positionAdapter) {
        presenter.changeFile(fileInfo);
    }

    @Override
    public void onClickMoveFile(FileInfo fileInfo, int positionAdapter) {
        presenter.moveFile(fileInfo);
    }

    @Override
    public void onClickRenameFile(FileInfo fileInfo, int positionAdapter) {
        presenter.renameFile(fileInfo);
    }

    @Override
    public void onClickDownloadFile(FileInfo fileInfo, int positionAdapter) {
        presenter.downloadFile(fileInfo);
    }

    @Override
    public UserConnection getExtraUserConnection() {
        if(getArguments() != null && getArguments().containsKey(Constans.EXTRA_USER_CONNECTION)){
            return getArguments().getParcelable(Constans.EXTRA_USER_CONNECTION);
        }
        return null;
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
