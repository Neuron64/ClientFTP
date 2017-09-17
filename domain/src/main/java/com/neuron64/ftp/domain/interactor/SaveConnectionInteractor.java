package com.neuron64.ftp.domain.interactor;

import android.util.Log;

import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.ConnectionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Neuron on 17.09.2017.
 */

@Singleton
public class SaveConnectionInteractor {

    private static final String TAG = SaveConnectionInteractor.class.getSimpleName();

    private ConnectionRepository repository;

    @Inject
    public SaveConnectionInteractor(ConnectionRepository repository) {
        this.repository = repository;
    }

    public void saveConnection(UserConnection userConnection){
        try {
            repository.saveConnection(userConnection);
        } catch (Exception e) {
            Log.e(TAG, "saveConnection: ", e);
        }
    }
}
