package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.FtpRepository;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yks-11 on 10/18/17.
 */

public class FtpConnectionUseCase extends CompletableUseCase<UserConnection>{

    private FtpRepository ftpRepository;

    @Inject
    public FtpConnectionUseCase(@NonNull BaseSchedulerProvider schedulerProvider, @NonNull FtpRepository ftpRepository) {
        super(schedulerProvider);
        this.ftpRepository = Preconditions.checkNotNull(ftpRepository);
    }

    @Override
    public Completable buildCompletable(UserConnection userConnection) {
        return ftpRepository.connect(userConnection.getId(),
                userConnection.getNameConnection(),
                userConnection.getHost(),
                userConnection.getUserName(),
                userConnection.getPassword(),
                userConnection.getPort());
    }

    public void disconnectExecute(@NonNull Action onComplete,
                                  @NonNull Consumer<? super Throwable> onError){
        Disposable disposable = ftpRepository.disconnect()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onComplete, onError);

        disposables.add(disposable);
    }
}
