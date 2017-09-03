package com.neuron64.ftp.client.login;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.util.ActivityUtils;
import com.neuron64.ftp.data.repository.RepositoryProvider;
import com.neuron64.ftp.domain.usecase.ConnectionUseCase;

/**
 * Created by Neuron on 02.09.2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//        Toolbar toolbar = findViewById(R.id);
//        setSupportActionBar(toolbar);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(loginFragment == null){
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        ConnectionUseCase connectionUseCase = new ConnectionUseCase(RepositoryProvider.getConnectionRepository());
        new LoginPresenter(loginFragment, connectionUseCase);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
