package com.neuron64.ftp.data.network;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by Neuron on 03.09.2017.
 */

public final class ApiFactory {

    private static FTPClient ftpClient;

    public static FTPClient getFtpClient() {
        if (ftpClient == null) {
            ftpClient = createService();
        }
        return ftpClient;
    }

    private static FTPClient createService() {
        return new FTPClient();
    }
}
