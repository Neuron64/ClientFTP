package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
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

    protected abstract Single<List<FileInfo>> getRootDirectory();

    protected abstract Single<List<FileInfo>> getPreviousDirectory();

    public abstract Single<List<FileInfo>> getNextDirectory(Params params);

    public abstract Completable deleteDocument(Params params);

    public void executeRootDirectory(Consumer<? super List<FileInfo>> onSuccess,
                                     Consumer<? super Throwable> onError,
                                     Consumer<? super Disposable> accept,
                                     Action run){
        Single<List<FileInfo>> single = getRootDirectory();
        if(single != null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void executeNextDirectory(Consumer<? super List<FileInfo>> onSuccess,
                                     Consumer<? super Throwable> onError,
                                     Consumer<? super Disposable> accept,
                                     Action run,
                                     Params params){
        Single<List<FileInfo>> single = getNextDirectory(params);
        if(single != null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void executePreviousDirectory(Consumer<? super List<FileInfo>> onSuccess,
                                         Consumer<? super Throwable> onError,
                                         Consumer<? super Disposable> accept,
                                         Action run){
        Single<List<FileInfo>> single = getPreviousDirectory();
        if(single != null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void executeDeleteDocument(@NonNull Action onComplete,
                                      @NonNull Consumer<? super Throwable> onError,
                                      @NonNull Consumer<? super Disposable> accept,
                                      Params params){
        Completable completable = deleteDocument(params);

        if(completable != null) {
            completable = completable.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept);
            disposables.add(completable.subscribe(onComplete, onError));
        }
    }

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.clear();
        }
    }
}
