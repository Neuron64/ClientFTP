package com.neuron64.ftp.domain.repository;

import com.neuron64.ftp.domain.model.UserConnection;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Neuron on 24.09.2017.
 */

public interface FtpRepository extends FileSystemRepository{

    Completable testConnection(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port);

    Completable connect(String id, String nameConnection, String host, String userName, String password, String port);

    Completable disconnect();
}
