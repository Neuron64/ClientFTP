package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.Nullable;

/**
 * Created by yks-11 on 10/13/17.
 */

public class GetDirectoryesUseCase extends SingleUseCase<List<FileSystemDirectory>, String>{

    private FileSystemRepository fileSystemRepository;

    @Inject
    public GetDirectoryesUseCase(BaseSchedulerProvider schedulerProvider, FileSystemRepository fileSystemRepository) {
        super(schedulerProvider);
        this.fileSystemRepository = fileSystemRepository;
    }

    @Override
    public Single<List<FileSystemDirectory>> buildUseCase(@Nullable String directoryId) {
        return fileSystemRepository.getExternalStorageFiles(directoryId);
    }
}
