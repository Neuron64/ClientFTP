package com.neuron64.ftp.data.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import static com.neuron64.ftp.data.content_provider.ExternalStorageProvider.AUTHORITY;

/**
 * Created by Neuron on 22.10.2017.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean deleteContents(File dir) {
        File[] files = dir.listFiles();
        boolean success = true;
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    success &= deleteContents(file);
                }
                if (!file.delete()) {
                    Log.w(TAG, "Failed to delete " + file);
                    success = false;
                }
            }
        }
        return success;
    }


}
