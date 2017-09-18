package com.neuron64.ftp.data.database;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Created by Neuron on 03.09.2017.
 */

public final class RealmService {

    public static Observable<List<UserConnection>> getAllUserConnection(){
        return Observable.fromCallable(() -> Realm.getDefaultInstance().where(UserConnection.class).findAll());
    }

    public static void saveConnection(UserConnection userConnection){
        Realm.getDefaultInstance().insert(userConnection);
    }
}
