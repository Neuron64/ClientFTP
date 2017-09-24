package com.neuron64.ftp.client.ui.base.bus;

import android.os.Handler;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by Neuron on 17.09.2017.
 */

/**
 * Используется для обмена:
 * fragment -> activity
 * activity -> fragment
**/
public class RxBus {

    private final Relay<Object> bus = PublishRelay.create().toSerialized();
    private volatile boolean isLock;
    private Handler handler = new Handler();

    public void send(Object o){
        bus.accept(o);
    }

    public void sendWithLock(Object event, long lockOutTime){
        if(!isLock){
            isLock = true;
            send(event);
            handler.postDelayed(() -> isLock = false, lockOutTime);
        }
    }

    public void sendWithLock(Object event){
        sendWithLock(event, 500);
    }

    public Flowable<Object> asFlowable(){
        return bus.toFlowable(BackpressureStrategy.LATEST);
    }

    public boolean hasObserver(){
        return bus.hasObservers();
    }
}
