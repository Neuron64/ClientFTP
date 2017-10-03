package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.exception.InvalidHostException;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;
import com.neuron64.ftp.domain.util.UtilUUID;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Neuron on 18.09.2017.
 */
//TODO: может не стоит кидать Scheduler.IO ??

public class CreateConnectionUserCase extends UseCase<UserConnection, Void>{

    @NonNull
    private ConnectionRepository repository;

    private String title;
    private String host;
    private String username;
    private String password;
    private String port;
    private String id;

    @Inject
    public CreateConnectionUserCase(BaseSchedulerProvider schedulerProvider, ConnectionRepository repository) {
        super(schedulerProvider);
        this.repository = checkNotNull(repository);
    }

    public CreateConnectionUserCase init(@Nullable String id, @Nullable String title, @Nullable String host, @Nullable String username, @Nullable String password, @Nullable String port){
        this.title = title;
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.id = id;

        return this;
    }

    @Override
    public Observable<UserConnection> buildUseCase(Void v) {
        Date nowDate = new Date();
        String idConnection = id != null? id : UtilUUID.generateUUID();
        Single<UserConnection> single = repository.saveOrUpdateConnection(idConnection, title, host, username, password, port, nowDate);
        return Observable.concat(validate(), single.toObservable());
    }

    private Observable<UserConnection> validate() {
        return Observable.create(subscriber -> {
            if (host.isEmpty()) {
                subscriber.onError(new InvalidHostException());
            }else{
                subscriber.onComplete();
            }
        });
    }
}
