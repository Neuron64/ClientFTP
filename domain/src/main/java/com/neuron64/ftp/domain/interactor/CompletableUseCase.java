package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public abstract class CompletableUseCase<Params> {

    protected BaseSchedulerProvider schedulerProvider;
    protected CompositeDisposable disposables;

    public CompletableUseCase(@NonNull BaseSchedulerProvider schedulerProvider){
        this.schedulerProvider = schedulerProvider;
        this.disposables = new CompositeDisposable();
    }

    public void execute(@NonNull Action onComplete,
                        @NonNull Consumer<? super Throwable> onError,
                        Params params){
        Disposable disposable = buildCompletable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onComplete,onError);
        disposables.add(disposable);
    }

    public void execute(@NonNull Action onComplete,
                        @NonNull Consumer<? super Throwable> onError,
                        @NonNull Consumer<? super Disposable> accept,
                        Params params){
        Disposable disposable = buildCompletable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(accept)
                .subscribe(onComplete,onError);
        disposables.add(disposable);
    }

    public abstract Completable buildCompletable(Params params);

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.clear();
        }
    }
}