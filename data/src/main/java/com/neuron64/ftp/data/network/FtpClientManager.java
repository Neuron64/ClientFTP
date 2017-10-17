package com.neuron64.ftp.data.network;

import com.neuron64.ftp.data.exception.ErrorConnectionFtp;

import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by yks-11 on 10/16/17.
 */

public interface FtpClientManager {

    void testConnection(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port) throws IOException, ErrorConnectionFtp;

    void connect(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port) throws IOException, ErrorConnectionFtp;

    List<FTPFile> getListRootFolder() throws IOException;

    List<FTPFile> getListFolder(String pathName) throws IOException;

}
