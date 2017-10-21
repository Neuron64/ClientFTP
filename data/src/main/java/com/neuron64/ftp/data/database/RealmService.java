package com.neuron64.ftp.data.database;

import android.support.annotation.NonNull;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.List;

import io.reactivex.Completable;

/**
 * Created by Neuron on 24.09.2017.
 */

public interface RealmService {

    List<UserConnection> getAllUserConnection();

    void insertOrUpdateConnection(UserConnection userConnection);

    void deleteConnection(@NonNull  String id);
}
