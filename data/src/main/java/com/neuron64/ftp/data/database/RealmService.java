package com.neuron64.ftp.data.database;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Neuron on 24.09.2017.
 */

public interface RealmService {

    Single<List<UserConnection>> getAllUserConnection();

    Completable saveConnection(UserConnection userConnection);

}
