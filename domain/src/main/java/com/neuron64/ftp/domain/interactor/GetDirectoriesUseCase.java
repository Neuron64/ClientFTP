package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by yks-11 on 10/13/17.
 */

public class GetDirectoriesUseCase extends DirectoryUseCase<String>{

    @Inject
    public GetDirectoriesUseCase(FileSystemRepository fileSystemRepository, BaseSchedulerProvider schedulerProvider) {
        super(fileSystemRepository, schedulerProvider);
    }

    @Override
    public Single<List<FileInfo>> getRootDirectory() {
        return fileSystemRepository.getExternalStorageFiles();
    }

    @Override
    public Single<List<FileInfo>> getPreviousDirectory() {
        return fileSystemRepository.getPreviousFiles();
    }

    @Override
    public Single<List<FileInfo>> getNextDirectory(String documentId) {
        return fileSystemRepository.getNextFiles(documentId);
    }

    @Override
    public Completable deleteDocument(String documentId) {
        return fileSystemRepository.deleteFile(documentId);
    }
}
