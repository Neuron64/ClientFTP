package com.neuron64.ftp.data.network;

import com.neuron64.ftp.data.exception.ErrorConnectionFtp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yks-11 on 10/16/17.
 */

public class IFtpClientManager implements FtpClientManager{

    private static final String TAG = "IFtpClientManager";

    private FTPClient ftpClient;

    public IFtpClientManager(){
        ftpClient = new FTPClient();
    }

    @Override
    public void testConnection(String host, String username, String password, Integer port) throws IOException, ErrorConnectionFtp {
        try {
            ftpClient.connect(host, port);
            if (!ftpClient.login(username, password)) {
                throw new ErrorConnectionFtp();
            }
        } finally {
            if(ftpClient != null) {
                ftpClient.disconnect();
            }
        }
    }

    @Override
    public void connect(String host, String username, String password, Integer port) throws IOException, ErrorConnectionFtp {
        ftpClient.connect(host, port);
        if (!ftpClient.login(username, password)) {
            throw new ErrorConnectionFtp();
        }
    }

    @Override
    public void disconnect() throws IOException {
        ftpClient.disconnect();
    }

    @Override
    public List<FTPFile> getListFolder(String pathName) throws IOException {
        return Arrays.asList(ftpClient.listFiles(pathName));
    }
}
