package com.neuron64.ftp.client.ui.base.bus.event;

import android.os.Bundle;

public class ExposeEvent {

    public static final int CREATE_CONNECTION = 0;
    public static final int SHOW_CONNECTIONS = 1;

    public final int code;
    public final Bundle data;

    private ExposeEvent(Bundle data, int code){
        this.data = data;
        this.code = code;
    }

    public static ExposeEvent exposeCreateConnection(Bundle data){
        return new ExposeEvent(data, CREATE_CONNECTION);
    }

    public static ExposeEvent exposeShowConnection(Bundle data){
        return new ExposeEvent(data, SHOW_CONNECTIONS);
    }
}
