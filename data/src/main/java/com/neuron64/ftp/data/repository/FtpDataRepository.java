package com.neuron64.ftp.data.repository;

import com.neuron64.ftp.data.mapper.FtpFileMapper;
import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FtpRepository;

import org.apache.commons.net.ftp.FTPFile;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;

/**
 * Created by Neuron on 24.09.2017.
 */

public class FtpDataRepository implements FtpRepository{

    private FtpClientManager ftpClientManager;
    private FtpFileMapper ftpFileMapper;

    @Inject
    public FtpDataRepository(@NonNull FtpClientManager ftpClientManager){
        this.ftpClientManager = ftpClientManager;
    }

    @Override
    public Completable testConnection(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port) {
        return Completable.fromAction(() -> ftpClientManager.testConnection(host, username, password, port));
    }

    @Override
    public Single<List<FileSystemDirectory>> getExternalStorageFiles() {
        return Single.fromCallable(() -> ftpClientManager.getListRootFolder())
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(ftpFiles -> ftpFileMapper.map(ftpFiles))
                .toList();
    }

    @Override
    public Single<List<FileSystemDirectory>> getNextFiles(String pathName) {
        return Single.fromCallable(() -> ftpClientManager.getListFolder(pathName))
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(ftpFiles -> ftpFileMapper.map(ftpFiles))
                .toList();
    }

    @Override
    public Single<List<FileSystemDirectory>> getPreviousFiles() {
//        return Single.fromCallable(() -> ftpClientManager.getListFolder(pathName))
//                .toObservable()
//                .flatMap(Observable::fromIterable)
//                .map(ftpFiles -> ftpFileMapper.map(ftpFiles))
//                .toList();
        return null;
    }
}
