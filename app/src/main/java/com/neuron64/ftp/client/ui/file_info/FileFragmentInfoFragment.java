package com.neuron64.ftp.client.ui.file_info;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerFileComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.FileInfo;

import javax.inject.Inject;

/**
 * Created by Neuron on 21.10.2017.
 */

public class FileFragmentInfoFragment extends BaseFragment implements FileFragmentContact.View{

    public static final String TAG = "FileFragmentInfoFragmen";

    private FileFragmentContact.Presenter presenter;

    public static FileFragmentInfoFragment newInstance(FileInfo fileInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constans.EXTRA_FILE_INFO, fileInfo);
        FileFragmentInfoFragment fragment = new FileFragmentInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public FileInfo getExtraFileInfo(){
        if(getArguments() != null && getArguments().containsKey(Constans.EXTRA_FILE_INFO)){
            return getArguments().getParcelable(Constans.EXTRA_FILE_INFO);
        }else return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter != null) {
            presenter.subscribe();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null){
            presenter.unsubscribe();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_file_info;
    }

    @Inject @Override
    public void attachPresenter(@NonNull FileFragmentContact.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
        this.presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        super.initializeDependencies();
        DaggerFileComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}
