package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Neuron on 17.09.2017.
 */

public abstract class SingleUseCase<T, Params> {

    protected BaseSchedulerProvider schedulerProvider;
    protected CompositeDisposable disposables;

    public SingleUseCase(BaseSchedulerProvider schedulerProvider){
        this.schedulerProvider=schedulerProvider;
        this.disposables = new CompositeDisposable();
    }

    public abstract Single<T> buildUseCase(Params params);

    public void execute(DisposableSingleObserver<T> singleObserver, Params params){
        Single<T> single = buildUseCase(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
        disposables.add(single.subscribeWith(singleObserver));
    }

    public void execute(Consumer<? super T> onSuccess, Consumer<? super Throwable> onError, Params params){
        Single<T> single= buildUseCase(params);
        if(single!=null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui());
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void execute(Consumer<? super T> onSuccess, Consumer<? super Throwable> onError, Consumer<? super Disposable> accept, Action run, Params params){
        Single<T> single= buildUseCase(params);
        if(single!=null) {
            single = single.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(single.subscribe(onSuccess, onError));
        }
    }

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.clear();
        }
    }
}
