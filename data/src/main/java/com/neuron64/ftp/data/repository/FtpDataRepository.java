package com.neuron64.ftp.data.repository;

import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.FtpRepository;

import org.apache.commons.net.ftp.FTPFile;

import java.util.ArrayList;
import java.util.List;

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
    private Mapper<FileSystemDirectory, FTPFile> ftpFileMapper;

    private List<String> previousDirectory;

    private UserConnection connectionInfo;

    @Inject
    public FtpDataRepository(@NonNull FtpClientManager ftpClientManager, @NonNull Mapper<FileSystemDirectory, FTPFile> ftpFileMapper){
        this.ftpClientManager = ftpClientManager;
        this.ftpFileMapper = ftpFileMapper;
        this.previousDirectory = new ArrayList<>();
    }

    @Override
    public Completable testConnection(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port) {
        return Completable.fromAction(() -> ftpClientManager.testConnection(host, username, password, port));
    }

    @Override
    public Completable connect(UserConnection config) {
        connectionInfo = config;

        return Completable.fromAction(() ->
                ftpClientManager.connect(config.getHost(),
                        config.getUserName(),
                        config.getPassword(),
                        Integer.parseInt(config.getPort())));
    }

    @Override
    public void disconnect() {
        //TODO: disconnect
    }

    @Override
    public Single<List<FileSystemDirectory>> getExternalStorageFiles() {
        previousDirectory.add("");
        return Single.fromCallable(() -> ftpClientManager.getListRootFolder()).to(funToFileSystemDto);
    }

    @Override
    public Single<List<FileSystemDirectory>> getNextFiles(String pathName) {
        //TODO:Need full path
        previousDirectory.add(pathName + "/");
        return Single.fromCallable(() -> ftpClientManager.getListFolder(pathName)).to(funToFileSystemDto);
    }

    @Override
    public Single<List<FileSystemDirectory>> getPreviousFiles() {
        String previous = previousDirectory.remove(previousDirectory.size() - 1);
        return Single.fromCallable(() -> ftpClientManager.getListFolder(previous)).to(funToFileSystemDto);
    }

    private final Function<Single<List<FTPFile>>, Single<List<FileSystemDirectory>>> funToFileSystemDto = new Function<Single<List<FTPFile>>, Single<List<FileSystemDirectory>>>() {
        @Override
        public Single<List<FileSystemDirectory>> apply(Single<List<FTPFile>> listSingle) throws Exception {
            return listSingle.toObservable()
                    .flatMap(Observable::fromIterable)
                    .map(ftpFiles -> ftpFileMapper.map(ftpFiles))
                    .toList();
        }
    };
}
