package com.neuron64.ftp.domain.executor;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Neuron on 17.09.2017.
 */

public class SchedulerProvider implements BaseSchedulerProvider {

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler multi() {
        return Schedulers.newThread();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
