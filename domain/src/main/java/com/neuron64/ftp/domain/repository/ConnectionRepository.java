package com.neuron64.ftp.domain.repository;

import com.neuron64.ftp.domain.model.UserConnection;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Neuron on 03.09.2017.
 */

public interface ConnectionRepository {

    Single<List<UserConnection>> getAllConnection();

    Single<UserConnection> saveConnection(@NonNull String id, @Nullable String title, @NonNull String host, @Nullable String username, @Nullable String password, @Nullable String port, @Nullable Date date);
}
