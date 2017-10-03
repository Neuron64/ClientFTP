package com.neuron64.ftp.client.ui.base;

import android.app.Activity;
import android.content.Intent;

import com.neuron64.ftp.client.ui.base.bus.event.NavigateEvent;
import com.neuron64.ftp.client.ui.directory.DirectoryActivity;
import com.neuron64.ftp.client.util.Constans;

/**
 * Created by Neuron on 01.10.2017.
 */

public class Navigator {

    public void navigate(Activity activity, NavigateEvent navigateEvent){
        Class<?> clazz = DirectoryActivity.class;
        switch (navigateEvent.code){
            case NavigateEvent.OPEN_DIRECTORY: {
                clazz = DirectoryActivity.class;
                break;
            }
        }

        Intent intent = new Intent(activity, clazz);
        intent.putExtra(Constans.EXTRA_DATA, navigateEvent.data);
        activity.startActivity(intent);
    }
}
