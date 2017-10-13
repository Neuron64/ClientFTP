package com.neuron64.ftp.data.content_provider;


import android.provider.DocumentsProvider;

/**
 * Created by yks-11 on 10/5/17.
 */

public abstract class StorageProvider extends DocumentsProvider {

    private static final String TAG = "StorageProvider";

    public abstract void updateRoots();

}
