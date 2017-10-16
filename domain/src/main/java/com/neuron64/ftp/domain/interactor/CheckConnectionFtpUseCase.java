package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.exception.InvalidHostException;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.repository.FtpRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Neuron on 24.09.2017.
 */

public class CheckConnectionFtpUseCase extends CompletableUseCase<Void>{

    private String host;
    private String username;
    private String password;
    private String port;

    @NonNull
    private FtpRepository repository;

    @Inject
    public CheckConnectionFtpUseCase(BaseSchedulerProvider schedulerProvider, FtpRepository repository) {
        super(schedulerProvider);
        this.repository = checkNotNull(repository);
    }

    public CheckConnectionFtpUseCase init(String host, String username, String password, String port){
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;

        return this;
    }

    @Override
    public Completable buildCompletable(Void aVoid) {
        Integer myPort = port.isEmpty() ? null : Integer.parseInt(port);
        return validate().andThen(repository.testConnection(host, username, password, myPort));
    }

    private Completable validate() {
        return Completable.create(subscriber -> {
            if (host.isEmpty()) {
                subscriber.onError(new InvalidHostException());
            }else{
                subscriber.onComplete();
            }
        });
    }
}
