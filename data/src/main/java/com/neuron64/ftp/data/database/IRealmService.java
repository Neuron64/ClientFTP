package com.neuron64.ftp.data.database;

import android.support.annotation.NonNull;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.realm.Realm;

/**
 * Created by Neuron on 03.09.2017.
 */

@Singleton
public final class IRealmService implements RealmService{

    public List<UserConnection> getAllUserConnection(){
        try (Realm realm = getRealm()) {
            return realm.copyFromRealm(realm.where(UserConnection.class).findAll());
        }
    }

    public void insertOrUpdateConnection(UserConnection userConnection){
        try (Realm realm = getRealm()) {
            realm.executeTransaction(realm1 -> realm1.insertOrUpdate(userConnection));
        }
    }

    @Override
    public void deleteConnection(@NonNull String id) {
        try (Realm realm = getRealm()) {
            realm.executeTransaction(realm1 -> realm1.where(UserConnection.class)
                    .equalTo(UserConnection.FIELD_ID, id).findAll().deleteAllFromRealm());
        }
    }

    private Realm getRealm(){
        return Realm.getDefaultInstance();
    }
}
