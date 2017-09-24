package com.neuron64.ftp.data.repository;

import com.neuron64.ftp.data.exception.ErrorConnectionFtp;
import com.neuron64.ftp.domain.repository.FtpRepository;

import org.apache.commons.net.ftp.FTPClient;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Neuron on 24.09.2017.
 */

public class FtpDataRepository implements FtpRepository{

    @Override
    public Completable checkConnection(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port) {
        return Completable.fromAction(() -> {
            FTPClient ftpClient = null;
            try {
                ftpClient = new FTPClient();
                ftpClient.connect(host, port);
                if (!ftpClient.login(username, password)) {
                    throw new ErrorConnectionFtp();
                }
            }finally {
                if(ftpClient != null) {
                    ftpClient.disconnect();
                }
            }
        });
    }
}
