package com.neuron64.ftp.client.ui.file_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.util.ActivityUtils;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.domain.model.FileInfo;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Neuron on 21.10.2017.
 */

public class FileActivity extends BaseActivity{

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_activity);
        unbinder = ButterKnife.bind(this);

        if(savedInstanceState == null){
            FileInfo fileInfo = getBundleExtras();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), FileFragmentInfoFragment.newInstance(fileInfo), R.id.contentFrame, FileFragmentInfoFragment.TAG);
        }
    }

    private FileInfo getBundleExtras(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey(Constans.EXTRA_FILE_INFO)){
            return bundle.getParcelable(Constans.EXTRA_FILE_INFO);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        //TODO: Catch Handle Events
    }

    @Override
    public void inject() {
        App.getAppInstance()
                .getAppComponent()
                .inject(this);
    }

}
