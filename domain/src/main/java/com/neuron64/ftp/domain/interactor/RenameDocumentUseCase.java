package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.exception.NameNotValide;
import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import io.reactivex.Completable;

/**
 * Created by yks-11 on 11/15/17.
 */

public abstract class RenameDocumentUseCase<Params> extends CompletableUseCase<Params>{

    protected FileSystemRepository repository;

    public RenameDocumentUseCase(BaseSchedulerProvider schedulerProvider, FileSystemRepository repository) {
        super(schedulerProvider);
        this.repository = Preconditions.checkNotNull(repository);
    }

    protected abstract Completable renameDocument(Params params);

    protected abstract Completable validate(Params params);

    @Override
    public Completable buildCompletable(Params params) {
        return validate(params).andThen(renameDocument(params));
    }
}
