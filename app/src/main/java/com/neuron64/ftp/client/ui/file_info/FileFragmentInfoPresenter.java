package com.neuron64.ftp.client.ui.file_info;

import android.support.annotation.NonNull;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.FileInfo;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Neuron on 21.10.2017.
 */

public class FileFragmentInfoPresenter implements FileFragmentContact.Presenter {

    private static final String TAG = "FileFragmentInfoPresent";

    private FileFragmentContact.View view;

    private RxBus eventBus;

    private CompositeDisposable disposables;

    @Inject
    public FileFragmentInfoPresenter(@NonNull RxBus eventBus){
        this.eventBus = Preconditions.checkNotNull(eventBus);
    }

    @Override
    public void attachView(@NonNull FileFragmentContact.View view) {
        this.view = Preconditions.checkNotNull(view);
    }

    @Override
    public void subscribe() {
        disposables = new CompositeDisposable();
        disposables.add(eventBus.asFlowable().subscribe(this::handleEvent));

        initPresenter();
    }

    @Override
    public void unsubscribe() {
        disposables.dispose();
    }

    private void initPresenter(){
        FileInfo info = view.getExtraFileInfo();
        view.showInfoAboutFile(info.getTitle(),
                info.getType(),
                info.getDateLastModification(),
                info.getPath(),
                info.getAvailableBytes());
    }

    private void handleEvent(Object event){
        //TODO: Catch handle events
    }
}
