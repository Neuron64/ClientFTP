package com.neuron64.ftp.client.ui.directory.file_system;

import android.support.annotation.NonNull;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryFileSystemPresenter extends DirectoryPresenter<DirectoryFileSystemContact.View> implements DirectoryFileSystemContact.Presenter {

    private static final String TAG = "DirectoryFileSystemPresenter";

    public DirectoryFileSystemPresenter(@NonNull RxBus rxBus, @NonNull GetDirectoriesUseCase getDirectoriesUseCase){
        super(rxBus, getDirectoriesUseCase);
    }

    @Override
    public void subscribe() {
        view.showLoadingIndicator();
        super.subscribe();
    }

    @Override
    public void attachView(@NonNull DirectoryFileSystemContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    @Override
    protected String getSimpleNameClass() {
        return this.getClass().getSimpleName();
    }
}
