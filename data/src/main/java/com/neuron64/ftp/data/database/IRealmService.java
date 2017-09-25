package com.neuron64.ftp.data.database;

import android.support.annotation.NonNull;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Neuron on 03.09.2017.
 */

@Singleton
public final class IRealmService implements RealmService{

    public final Single<List<UserConnection>> getAllUserConnection(){
        return Single.fromCallable(() -> Realm.getDefaultInstance().where(UserConnection.class).findAll());
    }

    public final Completable insertOrUpdateConnection(UserConnection userConnection){
        return Completable.fromAction(() -> Realm.getDefaultInstance()
                .executeTransaction(realm1 -> realm1.insertOrUpdate(userConnection)));
    }

    @Override
    public Completable deleteConnection(@NonNull String id) {
        return Completable.fromAction(() -> Realm.getDefaultInstance()
                .executeTransaction(realm -> realm.where(UserConnection.class)
                        .equalTo(UserConnection.FIELD_ID, id).findAll().deleteAllFromRealm()));
    }
}
