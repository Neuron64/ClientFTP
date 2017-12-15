package com.neuron64.ftp.client.ui.directory.file_system;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.directory.DirectoryPresenter;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;
import com.neuron64.ftp.domain.interactor.MoveDocumentUseCase;
import com.neuron64.ftp.domain.interactor.RenameDocumentUseCase;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.params.MoveFileParams;
import com.neuron64.ftp.domain.params.RenameFileParams;

import io.reactivex.functions.Action;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryFileSystemPresenter extends DirectoryPresenter<DirectoryFileSystemContact.View> implements DirectoryFileSystemContact.Presenter {

    private static final String TAG = "DirectoryFileSystemPresenter";

    public DirectoryFileSystemPresenter(@NonNull RxBus rxBus,
                                        @NonNull GetDirectoriesUseCase getDirectoriesUseCase,
                                        @NonNull RenameDocumentUseCase<RenameFileParams> renameFileUseCase,
                                        @NonNull MoveDocumentUseCase<MoveFileParams> moveDocumentUseCase){
        super(rxBus, getDirectoriesUseCase, renameFileUseCase, moveDocumentUseCase);
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

    @Override
    protected void checkConnection(Action complete) {
        try {
            complete.run();
        } catch (Exception e) {
            Log.e(TAG, "checkConnection: ", e);
        }
    }

    @Override
    public void uploadFile(FileInfo fileInfo) {
        //TODO: Upload File!
    }
}
