package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.FtpRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by yks-11 on 10/17/17.
 */

public class GetFtpDirectoriesUseCase extends DirectoryUseCase<String> {

    private FtpRepository ftpRepository;

    private UserConnection userConnection;

    @Inject
    public GetFtpDirectoriesUseCase(FtpRepository fileSystemRepository, BaseSchedulerProvider schedulerProvider) {
        super(fileSystemRepository, schedulerProvider);
        this.ftpRepository = Preconditions.checkNotNull(fileSystemRepository);
    }

    public Completable initConfig(UserConnection userConnection){
        this.userConnection = Preconditions.checkNotNull(userConnection);
        return ftpRepository.connect(userConnection)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    @Override
    public Single<List<FileSystemDirectory>> getRootDirectory() {
        Preconditions.checkNotNull(userConnection);

        return fileSystemRepository.getExternalStorageFiles();
    }

    @Override
    public Single<List<FileSystemDirectory>> getPreviousDirectory() {
        Preconditions.checkNotNull(userConnection);

        return fileSystemRepository.getPreviousFiles();
    }

    @Override
    public Single<List<FileSystemDirectory>> getNextDirectory(String documentId) {
        Preconditions.checkNotNull(userConnection);

        return fileSystemRepository.getNextFiles(documentId);
    }
}
