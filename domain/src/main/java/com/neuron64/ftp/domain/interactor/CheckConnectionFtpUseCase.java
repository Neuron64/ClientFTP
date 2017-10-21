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

public class CheckConnectionFtpUseCase extends CompletableUseCase<CheckConnectionFtpUseCase.ConnectionParams>{

    @NonNull
    private FtpRepository repository;

    @Inject
    public CheckConnectionFtpUseCase(BaseSchedulerProvider schedulerProvider, FtpRepository repository) {
        super(schedulerProvider);
        this.repository = checkNotNull(repository);
    }

    private Completable validate(ConnectionParams connectionParams) {
        return Completable.create(subscriber -> {
            if (connectionParams.getHost().isEmpty()) {
                subscriber.onError(new InvalidHostException());
            }else{
                subscriber.onComplete();
            }
        });
    }

    @Override
    public Completable buildCompletable(ConnectionParams connectionParams) {
        return validate(connectionParams).andThen(repository.testConnection(connectionParams.host,
                connectionParams.username,
                connectionParams.password,
                connectionParams.port));
    }

    public static class ConnectionParams{

        private String host;
        private String username;
        private String password;
        private Integer port;

        public ConnectionParams(String host, String username, String password, Integer port) {
            this.host = host;
            this.username = username;
            this.password = password;
            this.port = port;
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
    }
}
