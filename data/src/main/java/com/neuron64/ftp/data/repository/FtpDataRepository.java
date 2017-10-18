package com.neuron64.ftp.data.repository;

import com.neuron64.ftp.data.ftp.ConnectionConfig;
import com.neuron64.ftp.data.mapper.Mapper;
import com.neuron64.ftp.data.network.FtpClientManager;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FtpRepository;

import org.apache.commons.net.ftp.FTPFile;

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

    private ConnectionConfig connectionConfig;

    @Inject
    public FtpDataRepository(@NonNull FtpClientManager ftpClientManager, @NonNull Mapper<FileSystemDirectory, FTPFile> ftpFileMapper){
        this.ftpClientManager = ftpClientManager;
        this.ftpFileMapper = ftpFileMapper;
        this.connectionConfig = new ConnectionConfig();
    }

    @Override
    public Completable testConnection(@NonNull String host, @Nullable String username, @Nullable String password, @Nullable Integer port) {
        return Completable.fromAction(() -> ftpClientManager.testConnection(host, username, password, port));
    }

    @Override
    public Completable connect(String id, String nameConnection, String host, String userName, String password, String port) {
        this.connectionConfig.initConfig(id, nameConnection, host, userName, password, port);

        return Completable.fromAction(() ->
                ftpClientManager.connect(connectionConfig.getHost(),
                        connectionConfig.getUserName(),
                        connectionConfig.getPassword(),
                        Integer.parseInt(connectionConfig.getPort())));
    }

    @Override
    public Completable disconnect() {
        return Completable.fromAction(() -> ftpClientManager.disconnect());
    }

    @Override
    public Single<List<FileSystemDirectory>> getExternalStorageFiles() {
        return Single.fromCallable(() -> ftpClientManager.getListFolder(connectionConfig.getFullPath())).to(funToFileSystemDto);
    }

    @Override
    public Single<List<FileSystemDirectory>> getNextFiles(String pathName) {
        return Single.fromCallable(() -> ftpClientManager.getListFolder(connectionConfig.addDirectory(pathName).getFullPath())).to(funToFileSystemDto);
    }

    @Override
    public Single<List<FileSystemDirectory>> getPreviousFiles() {
        return Single.fromCallable(() -> ftpClientManager.getListFolder(connectionConfig.backDirectory().getFullPath())).to(funToFileSystemDto);
    }

    private final Function<Single<List<FTPFile>>, Single<List<FileSystemDirectory>>> funToFileSystemDto =
            new Function<Single<List<FTPFile>>, Single<List<FileSystemDirectory>>>() {
        @Override
        public Single<List<FileSystemDirectory>> apply(Single<List<FTPFile>> listSingle) throws Exception {
            return listSingle.toObservable()
                    .flatMap(Observable::fromIterable)
                    .map(ftpFiles -> ftpFileMapper.map(ftpFiles))
                    .toList();
        }
    };
}
