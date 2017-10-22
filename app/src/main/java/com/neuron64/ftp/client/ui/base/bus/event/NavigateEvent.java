package com.neuron64.ftp.client.ui.base.bus.event;

import android.os.Bundle;

/**
 * Created by Neuron on 01.10.2017.
 */

public class NavigateEvent {

    public static final int OPEN_DIRECTORY = 0;
    public static final int OPEN_FILE_INFO = 1;

    public final int code;
    public final Bundle data;

    private NavigateEvent(Bundle data, int code){
        this.data = data;
        this.code = code;
    }

    public static NavigateEvent navigateOpenDirectory(Bundle data){
        return new NavigateEvent(data, OPEN_DIRECTORY);
    }

    public static NavigateEvent nabigateOpenFileInfo(Bundle data){
        return new NavigateEvent(data, OPEN_FILE_INFO);
    }
}
