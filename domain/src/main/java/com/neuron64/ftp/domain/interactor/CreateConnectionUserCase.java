package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.exception.InvalidLoginUsernameException;
import com.neuron64.ftp.domain.exception.InvalidPortException;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.util.UtilUUID;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Neuron on 18.09.2017.
 */
//TODO: может не стоит кидать Scheduler.IO ??

public class CreateConnectionUserCase extends UseCase<UserConnection, Void>{

    private ConnectionRepository repository;

    private String title;
    private String host;
    private String username;
    private String password;
    private String port;

    @Inject
    public CreateConnectionUserCase(BaseSchedulerProvider schedulerProvider, ConnectionRepository repository) {
        super(schedulerProvider);
        this.repository = checkNotNull(repository);
    }

    public CreateConnectionUserCase init(@Nullable String title, @Nullable String host, @Nullable String username, @Nullable String password, @Nullable String port){
        this.title = title;
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;

        return this;
    }

    @Override
    public Observable<UserConnection> buildUseCase(Void v) {
        Date nowDate = new Date();
        Single<UserConnection> single = repository.saveConnection(UtilUUID.generateUUID(), title, host, username, password, port, nowDate);
        return Observable.concat(validate(), single.toObservable());
    }

    private Observable<UserConnection> validate() {
        return Observable.create(subscriber -> {
            if (username.isEmpty()) {
                subscriber.onError(new InvalidPortException());
            } else if (password.isEmpty()) {
                subscriber.onError(new InvalidLoginUsernameException());
            } else if (host.isEmpty()) {
                subscriber.onError(new InvalidLoginUsernameException());
            } else if (port.isEmpty()) {
                subscriber.onError(new InvalidLoginUsernameException());
            } else if (title.isEmpty()) {
                subscriber.onError(new InvalidLoginUsernameException());
            } else {
                subscriber.isDisposed();
            }
        });
    }

}
