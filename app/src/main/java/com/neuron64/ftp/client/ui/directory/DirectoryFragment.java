package com.neuron64.ftp.client.ui.directory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.ui.base.RecyclerItemClickListener;
import com.neuron64.ftp.client.util.ViewMessage;
import com.neuron64.ftp.domain.model.FileInfo;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yks-11 on 10/17/17.
 */

public abstract class DirectoryFragment<A extends BaseAdapter<FileInfo>,
        P extends DirectoryContact.BaseDirectoryPresenter<?>> extends BaseFragment
        implements DirectoryContact.BaseDirectoryView<P>,
        RecyclerItemClickListener.OnItemClickListener{

    @Override
    public abstract void initializeDependencies();

    public abstract void attachPresenter(@NonNull P presenter);

    protected P presenter;

    protected A directoryAdapter;

    @BindView(R.id.rv_content) RecyclerView rvContent;
    @BindView(R.id.cl_root) ConstraintLayout clRoot;
    @BindView(R.id.ll_progress_bar) LinearLayout llProgressBar;
    @BindView(R.id.ll_empty_list) LinearLayout llEmptyList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);

        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvContent.setAdapter(directoryAdapter);
        rvContent.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvContent, this));

        return view;
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
    public void showFiles(List<FileInfo> files) {
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
        if(llEmptyList.getVisibility() == View.VISIBLE) {
            llEmptyList.setVisibility(View.GONE);
        }
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
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void clearRecyclerView() {
        directoryAdapter.clearItems();
    }

    @Override
    public void onItemClick(View view, int position) {
        FileInfo file = directoryAdapter.at(position);
        presenter.clickFile(file);
    }

    @Override
    public void onLongItemClick(View view, int position) {/*Empty*/}

    @Override
    public void removeDocument() {
        //TODO: Remove Document
    }

    @Override
    public void createFile() {
        //TODO: Create File
    }

    @Override
    public void moveFile() {
        //TODO: Move File
    }
}