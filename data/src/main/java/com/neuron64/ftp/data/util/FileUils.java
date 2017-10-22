package com.neuron64.ftp.data.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Created by Neuron on 22.10.2017.
 */

public class FileUils {

    public static void deleteFile(Context context, File file){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.fromFile(file);
        contentResolver.delete(uri, null, null);
    }

}
