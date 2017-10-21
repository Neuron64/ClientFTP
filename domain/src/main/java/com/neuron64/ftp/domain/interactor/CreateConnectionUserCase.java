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
public class CreateConnectionUserCase extends UseCase<UserConnection, CreateConnectionUserCase.ConnectionParams>{

    @NonNull
    private ConnectionRepository repository;

    @Inject
    public CreateConnectionUserCase(BaseSchedulerProvider schedulerProvider, ConnectionRepository repository) {
        super(schedulerProvider);
        this.repository = checkNotNull(repository);
    }

    @Override
    public Observable<UserConnection> buildUseCase(ConnectionParams connectionParams) {
        Date nowDate = new Date();
        String idConnection = connectionParams.getId() != null ? connectionParams.getId() : UtilUUID.generateUUID();
        Single<UserConnection> single = repository.saveOrUpdateConnection(idConnection,
                connectionParams.getTitle(),
                connectionParams.getHost(),
                connectionParams.getUsername(),
                connectionParams.getPassword(),
                connectionParams.getPort(),
                nowDate);

        return Observable.concat(validate(connectionParams), single.toObservable());
    }

    private Observable<UserConnection> validate(ConnectionParams connectionParams) {
        return Observable.create(subscriber -> {
            if (connectionParams.getHost().isEmpty()) {
                subscriber.onError(new InvalidHostException());
            }else{
                subscriber.onComplete();
            }
        });
    }

    public static class ConnectionParams{

        private String title;
        private String host;
        private String username;
        private String password;
        private Integer port;
        private String id;

        public ConnectionParams(@Nullable String id, @Nullable String title, @Nullable String host, @Nullable String username, @Nullable String password, @Nullable Integer port){
            this.title = title;
            this.host = host;
            this.username = username;
            this.password = password;
            this.port = port;
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public String getHost() {
            return host;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Integer getPort() {
            return port;
        }

        public String getId() {
            return id;
        }
    }
}
