package com.neuron64.ftp.client.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.neuron64.ftp.client.ui.base.bus.RxBus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Neuron on 17.09.2017.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected CompositeDisposable disposable;

    @Inject
    protected RxBus eventBus;

    public abstract void handleEvent(@NonNull Object event);

    public abstract void inject();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        disposable = new CompositeDisposable();
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        disposable.add(eventBus.asFlowable().subscribe(this::processEvent));
    }

    private void processEvent(Object object){
        if(object!=null){
            handleEvent(object);
        }
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();
        disposable.clear();
    }

    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
    }
}
