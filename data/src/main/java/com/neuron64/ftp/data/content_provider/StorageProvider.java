package com.neuron64.ftp.data.content_provider;


import android.net.Uri;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;

/**
 * Created by yks-11 on 10/5/17.
 */

public abstract class StorageProvider extends DocumentsProvider {

    private static final String TAG = "StorageProvider";

    public static final Uri FILE_URI = MediaStore.Files.getContentUri("external");

    public abstract void updateRoots();

}
