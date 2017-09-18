package com.neuron64.ftp.domain.interactor;

import com.neuron64.ftp.domain.executor.BaseSchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Neuron on 18.09.2017.
 */

public abstract class UseCase<T, Params> {

    protected BaseSchedulerProvider schedulerProvider;
    protected CompositeDisposable disposables;

    public UseCase(BaseSchedulerProvider schedulerProvider){
        this.schedulerProvider=schedulerProvider;
        this.disposables = new CompositeDisposable();
    }

    public abstract Observable<T> buildUseCase(Params params);

    public void execute(DisposableObserver<T> observer, Params params){
        Observable<T> observable = buildUseCase(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
        observable.subscribe(observer);
        disposables.add(observer);
    }

    public void execute(Consumer<? super T> onSuccess, Consumer<? super Throwable> onError, Params params){
        Observable<T> observable= buildUseCase(params);
        if(observable != null) {
            observable = observable.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui());
            disposables.add(observable.subscribe(onSuccess, onError));
        }
    }

    public void execute(Consumer<? super T> onSuccess, Consumer<? super Throwable> onError, Consumer<? super Disposable> accept, Action run, Params params){
        Observable<T> observable= buildUseCase(params);
        if(observable != null) {
            observable = observable.subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(accept)
                    .doAfterTerminate(run);
            disposables.add(observable.subscribe(onSuccess, onError));
        }
    }

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.clear();
        }
    }
}
