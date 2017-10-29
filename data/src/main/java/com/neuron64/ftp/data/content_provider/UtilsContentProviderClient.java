package com.neuron64.ftp.data.content_provider;

import android.content.ContentProviderClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Neuron on 29.10.2017.
 */

public class UtilsContentProviderClient {

    public static void releaseQuietly(ContentProviderClient client) {
        Method method = null;
        try {
            method = ContentProviderClient.class.getDeclaredMethod("releaseQuietly");
            method.setAccessible(true);
            method.invoke(ContentProviderClient.class, client);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
