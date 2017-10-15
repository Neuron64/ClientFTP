package com.neuron64.ftp.client.ui.connection;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.ui.base.Navigator;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.FragmentEvent;
import com.neuron64.ftp.client.ui.base.bus.event.NavigateEvent;
import com.neuron64.ftp.client.util.ActivityUtils;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.domain.model.UserConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Neuron on 02.09.2017.
 */

public class ConnectionActivity extends BaseActivity {

    private static final String TAG = "ConnectionActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Unbinder unbinder;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ConnectionsFragment.newInstance(), R.id.contentFrame, ConnectionsFragment.TAG);
        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS,
//                    Manifest.permission.READ_EXTERNAL_STORAGE};
//            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
//        }
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
        if(event instanceof FragmentEvent){
            FragmentEvent exposeEvent = (FragmentEvent) event;
            switch (exposeEvent.code){
                case FragmentEvent.CREATE_CONNECTION: {
                    CreateConnectionFragment fragment;
                    if(exposeEvent.data != null && exposeEvent.data.containsKey(Constans.EXTRA_USER_CONNECTION)){
                        UserConnection connection = exposeEvent.data.getParcelable(Constans.EXTRA_USER_CONNECTION);
                        fragment = CreateConnectionFragment.newInstance(connection);
                    }else{
                        fragment = CreateConnectionFragment.newInstance();
                    }

                    ActivityUtils.addFragmentToActivityWithBackStack(getSupportFragmentManager(), fragment, R.id.contentFrame, null, CreateConnectionFragment.TAG);
                    break;
                }
                case FragmentEvent.SHOW_CONNECTIONS: {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                    if(fragment instanceof CreateConnectionFragment){
                        getSupportFragmentManager().popBackStack();
                    }
                    break;
                }
            }
        }else if(event instanceof NavigateEvent){
            NavigateEvent navigateEvent = (NavigateEvent) event;

            Navigator navigator = new Navigator();
            navigator.navigate(this, navigateEvent);
        }
    }

    @Override
    public void inject() {
        App.getAppInstance().getAppComponent().inject(this);
    }
}
