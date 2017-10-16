package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yks-11 on 10/16/17.
 */

public abstract class DirectoryUseCase<Params> {

    protected FileSystemRepository fileSystemRepository;
    protected BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable disposables;

    public DirectoryUseCase(FileSystemRepository fileSystemRepository, BaseSchedulerProvider schedulerProvider){
        this.fileSystemRepository = fileSystemRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposables = new CompositeDisposable();
    }

    protected abstract Single<List<FileSystemDirectory>> getRootDirectory();

    protected abstract Single<List<FileSystemDirectory>> getPreviousDirectory();

    public abstract Single<List<FileSystemDirectory>> getNextDirectory(Params params);

    public void executeRootDirectory(Consumer<? super List<FileSystemDirectory>> onSuccess,
                                     Consumer<? super Throwable> onError,
                                     Consumer<? super Disposable> accept,
                                     Action run){
        Single<List<FileSystemDirectory>> single= getRootDirectory();
        if(single != null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void executeNextDirectory(Consumer<? super List<FileSystemDirectory>> onSuccess,
                                     Consumer<? super Throwable> onError,
                                     Consumer<? super Disposable> accept,
                                     Action run,
                                     Params params){
        Single<List<FileSystemDirectory>> single= getNextDirectory(params);
        if(single != null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void executePreviousDirectory(Consumer<? super List<FileSystemDirectory>> onSuccess,
                                         Consumer<? super Throwable> onError,
                                         Consumer<? super Disposable> accept,
                                         Action run){
        Single<List<FileSystemDirectory>> single= getPreviousDirectory();
        if(single != null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void dispose(){
        if(disposables != null) {
            disposables.dispose();
        }
    }
}
