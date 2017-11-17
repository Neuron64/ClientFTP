package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.exception.NameNotValide;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.params.RenameFileParams;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import io.reactivex.Completable;

/**
 * Created by yks-11 on 11/16/17.
 */

public class RenameFtpFileUseCase extends RenameDocumentUseCase<RenameFileParams>{

    @Inject
    public RenameFtpFileUseCase(BaseSchedulerProvider schedulerProvider, FileSystemRepository repository) {
        super(schedulerProvider, repository);
    }

    @Override
    protected Completable renameDocument(RenameFileParams params) {
        Preconditions.checkNotNull(params);
        Preconditions.checkNotNull(params.getIdDocument());

        return repository.renameConnection(params.getIdDocument(), params.getNewName());
    }

    @Override
    protected Completable validate(RenameFileParams params){
        return Completable.create(e -> {
            if(params.getNewName().isEmpty()){
                throw new NameNotValide();
            }
            e.onComplete();
        });
    }
}
