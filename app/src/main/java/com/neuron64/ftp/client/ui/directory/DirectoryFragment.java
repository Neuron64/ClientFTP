package com.neuron64.ftp.client.ui.directory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerApplicationComponent;
import com.neuron64.ftp.client.di.component.DaggerDirectoryComponent;
import com.neuron64.ftp.client.di.component.DaggerViewComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.ui.base.RecyclerItemClickListener;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.client.util.ViewMessage;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryFragment extends BaseFragment implements DirectoryContact.View, RecyclerItemClickListener.OnItemClickListener{

    public static final String TAG = "DirectoryFragment";

    private DirectoryContact.Presenter presenter;

    private DirectoryAdapter directoryAdapter;

    @BindView(R.id.rv_content) RecyclerView rvContent;
    @BindView(R.id.cl_root) ConstraintLayout clRoot;
    @BindView(R.id.ll_progress_bar) LinearLayout llProgressBar;
    @BindView(R.id.ll_empty_list) LinearLayout llEmptyList;

    public static DirectoryFragment newInstance() {
        Bundle args = new Bundle();

        DirectoryFragment fragment = new DirectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.directoryAdapter = new DirectoryAdapter(getContext(), presenter.getEventBus());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvContent.setAdapter(directoryAdapter);
        rvContent.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvContent, this));

        return view;
    }

    @Inject @Override
    public void attachPresenter(@NonNull DirectoryContact.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
        this.presenter.attachView(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.directory_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter != null){
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
    public void initializeDependencies() {
        DaggerDirectoryComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void showFiles(List<FileSystemDirectory> files) {
        directoryAdapter.setItems(files);
    }

    @Override
    public void showError() {
        ViewMessage.initSnackBarLong(clRoot, R.string.error);
    }

    @Override
    public void showEmptyList() {
        llEmptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyList() {
        llEmptyList.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        llProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        llProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position) {
        FileSystemDirectory file = directoryAdapter.at(position);
        presenter.clickFile(file);
    }

    @Override
    public void onLongItemClick(View view, int position) {/*Empty*/}
}
