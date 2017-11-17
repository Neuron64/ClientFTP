package com.neuron64.ftp.data.content_provider;

import android.content.ContentProviderClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Neuron on 29.10.2017.
 */

public class UtilsContentProviderClient {

    public static void releaseQuietly(ContentProviderClient client) {
//        client.release();
        Method method = null;
        client.release();
        try {
            method = ContentProviderClient.class.getDeclaredMethod("releaseQuietly", ContentProviderClient.class);
            method.setAccessible(true);
            method.invoke(null, client);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
