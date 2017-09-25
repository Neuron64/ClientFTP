package com.neuron64.ftp.data.database;

import android.support.annotation.NonNull;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Neuron on 24.09.2017.
 */

public interface RealmService {

    Single<List<UserConnection>> getAllUserConnection();

    Completable insertOrUpdateConnection(UserConnection userConnection);

    Completable deleteConnection(@NonNull  String id);
}
