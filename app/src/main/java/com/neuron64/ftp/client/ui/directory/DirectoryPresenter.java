package com.neuron64.ftp.client.ui.directory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.FragmentEvent;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.interactor.GetDirectoriesUseCase;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryPresenter implements DirectoryContact.Presenter {

    private static final String TAG = "DirectoryPresenter";

    private DirectoryContact.View view;

    private CompositeDisposable disposable;

    private GetDirectoriesUseCase getDirectoriesUseCase;

    @NonNull
    private RxBus eventBus;

    public DirectoryPresenter(@NonNull RxBus rxBus, @NonNull GetDirectoriesUseCase getDirectoriesUseCase){
        this.eventBus = Preconditions.checkNotNull(rxBus);
        this.getDirectoriesUseCase = Preconditions.checkNotNull(getDirectoriesUseCase);
    }

    @Override
    public void attachView(@NonNull DirectoryContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposable = new CompositeDisposable();
        disposable.add(eventBus.asFlowable().subscribe(this::handleEvent));

        initData();
    }

    private void initData(){
        goToFile(null);
    }

    public void goToFile(String directory){
        getDirectoriesUseCase.execute(fileSystemDirectories -> view.showFiles(fileSystemDirectories),
                throwable -> {
                    Log.e(TAG, "subscribe: throwable", throwable);
                    view.showEmptyList();
                    view.showError();
                }, accept -> view.showLoadingIndicator(), () -> view.hideLoadingIndicator(), directory);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    @Override
    public RxBus getEventBus() {
        return eventBus;
    }

    @Override
    public void clickFile(FileSystemDirectory file) {
        goToFile(file.getDocumentId());
    }

    private void handleEvent(Object event){
        //Empty
    }
}
