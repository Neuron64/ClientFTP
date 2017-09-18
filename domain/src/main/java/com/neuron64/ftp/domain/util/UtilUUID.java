package com.neuron64.ftp.domain.util;


import static java.util.UUID.randomUUID;

/**
 * Created by Neuron on 19.09.2017.
 */

public class UtilUUID {

    public static String generateUUID(){
        return randomUUID().toString();
    }

}
