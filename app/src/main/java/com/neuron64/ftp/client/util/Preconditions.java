package com.neuron64.ftp.client.util;

/**
 * Created by Neuron on 02.09.2017.
 */

public class Preconditions {

    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
