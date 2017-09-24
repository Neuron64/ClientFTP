package com.neuron64.ftp.client.ui.connection;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.ExposeEvent;
import com.neuron64.ftp.client.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Neuron on 02.09.2017.
 */

public class ConnectionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ConnectionsFragment loginFragment = (ConnectionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(loginFragment == null){
            loginFragment = ConnectionsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }
    }

    @OnClick(R.id.fab)
    void onClickFab(){
        eventBus.send(ButtonEvent.buttonEvent(ButtonEvent.TypeButtonEvent.FLOATING_BN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        if(event instanceof ExposeEvent){
            ExposeEvent exposeEvent = (ExposeEvent) event;
            switch (exposeEvent.code){
                case ExposeEvent.CREATE_CONNECTION: {
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), CreateConnectionFragment.newInstance(), R.id.contentFrame);
                    break;
                }
                case ExposeEvent.SHOW_CONNECTIONS: {
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ConnectionsFragment.newInstance(), R.id.contentFrame);
                    break;
                }
            }
        }
    }

    @Override
    public void inject() {
        App.getAppInstance().getAppComponent().inject(this);
    }
}
