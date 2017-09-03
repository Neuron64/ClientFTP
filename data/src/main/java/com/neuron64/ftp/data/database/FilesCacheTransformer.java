/*
package com.neuron64.ftp.data.database;

import com.neuron64.ftp.data.model.local.UserConnection;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import io.realm.RealmResults;

public class FilesCacheTransformer implements ObservableTransformer<List<UserConnection>, List<UserConnection>> {

    private final Function<List<UserConnection>, Observable<List<UserConnection>>> mSaveFunction = userConnections -> {
        Realm.getDefaultInstance().executeTransaction(realm -> {
        realm.delete(UserConnection.class);
        realm.insert(userConnections);
        });
        return Observable.just(userConnections);
    };

    @Override
    public ObservableSource<List<UserConnection>> apply(Observable<List<UserConnection>> upstream) {
        return upstream.flatMap(mSaveFunction);
    }
}*/
