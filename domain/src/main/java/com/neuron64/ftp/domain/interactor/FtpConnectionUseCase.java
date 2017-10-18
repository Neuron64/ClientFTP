package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.FtpRepository;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yks-11 on 10/18/17.
 */

public class FtpConnectionUseCase {

    private FtpRepository ftpRepository;
    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable disposables;

    @Inject
    public FtpConnectionUseCase(@NonNull BaseSchedulerProvider schedulerProvider, @NonNull FtpRepository ftpRepository) {
        this.schedulerProvider = Preconditions.checkNotNull(schedulerProvider);
        this.ftpRepository = Preconditions.checkNotNull(ftpRepository);

        this.disposables = new CompositeDisposable();
    }

    public void connectExecute(@NonNull Action onComplete,
                        @NonNull Consumer<? super Throwable> onError,
                        UserConnection userConnection) {
        Disposable disposable = ftpRepository.connect(userConnection.getId(),
                userConnection.getNameConnection(),
                userConnection.getHost(),
                userConnection.getUserName(),
                userConnection.getPassword(),
                userConnection.getPort())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onComplete, onError);

        disposables.add(disposable);
    }

    public void disconnectExecute(@NonNull Action onComplete,
                          @NonNull Consumer<? super Throwable> onError){
        Disposable disposable = ftpRepository.disconnect()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onComplete, onError);

        disposables.add(disposable);
    }

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.clear();
        }
    }
}
