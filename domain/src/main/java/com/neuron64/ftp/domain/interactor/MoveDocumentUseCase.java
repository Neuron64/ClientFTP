package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.params.MoveFileParams;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import dagger.internal.Preconditions;
import io.reactivex.Completable;

/**
 * Created by yks-11 on 11/17/17.
 */

public abstract class MoveDocumentUseCase<Params> extends CompletableUseCase<Params>{

    protected FileSystemRepository repository;

    public MoveDocumentUseCase(BaseSchedulerProvider schedulerProvider, FileSystemRepository repository) {
        super(schedulerProvider);
        this.repository = Preconditions.checkNotNull(repository);
    }

    protected abstract Completable moveDocument(Params params);

    protected abstract Completable validate(Params params);

    @Override
    public Completable buildCompletable(Params params) {
        return validate(params).andThen(moveDocument(params));
    }
}
