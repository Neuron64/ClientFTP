package com.neuron64.ftp.client.ui.file_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerFileComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.FileInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Neuron on 21.10.2017.
 */

public class FileFragmentInfoFragment extends BaseFragment implements FileFragmentContact.View{

    public static final String TAG = "FileFragmentInfoFragmen";

    private FileFragmentContact.Presenter presenter;

    @BindView(R.id.tv_name_file) TextView tvNameFile;
    @BindView(R.id.tv_extension_file) TextView tvExtensionFile;
    @BindView(R.id.tv_date_last_modification) TextView tvDateLastModification;
    @BindView(R.id.tv_path_file) TextView tvPathFile;
    @BindView(R.id.tv_file_size) TextView tvFileSize;

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public FileInfo getExtraFileInfo(){
        if(getArguments() != null && getArguments().containsKey(Constans.EXTRA_FILE_INFO)){
            return getArguments().getParcelable(Constans.EXTRA_FILE_INFO);
        }else return null;
    }

    @Override
    public void showInfoAboutFile(String name, String ext, Date date, String path, long size) {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.format_date_last_modification), Locale.getDefault());

        tvNameFile.setText(name);
        tvExtensionFile.setText(ext);
        tvPathFile.setText(path);
        tvFileSize.setText(getString(R.string.file_size_byte, size));
        tvDateLastModification.setText(dateFormat.format(date));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter != null) {
            presenter.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
