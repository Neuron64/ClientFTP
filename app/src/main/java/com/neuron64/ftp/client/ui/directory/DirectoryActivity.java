package com.neuron64.ftp.client.ui.directory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryActivity extends BaseActivity{

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_activity);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), DirectoryFragment.newInstance(), R.id.contentFrame, DirectoryFragment.TAG);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }

    @Override
    public void inject() {
        App.getAppInstance().getAppComponent().inject(this);
    }
}
