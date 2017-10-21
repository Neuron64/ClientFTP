package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.model.UserConnection;
import com.neuron64.ftp.domain.repository.FileSystemRepository;
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

    @Inject
    public GetFtpDirectoriesUseCase(FileSystemRepository fileSystemRepository, BaseSchedulerProvider schedulerProvider) {
        super(fileSystemRepository, schedulerProvider);
    }

    @Override
    public Single<List<FileSystemDirectory>> getRootDirectory() {
        return fileSystemRepository.getExternalStorageFiles();
    }

    @Override
    public Single<List<FileSystemDirectory>> getPreviousDirectory() {
        return fileSystemRepository.getPreviousFiles();
    }

    @Override
    public Single<List<FileSystemDirectory>> getNextDirectory(String documentId) {
        return fileSystemRepository.getNextFiles(documentId);
    }
}
