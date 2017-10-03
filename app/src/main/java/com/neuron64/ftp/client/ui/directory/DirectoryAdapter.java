package com.neuron64.ftp.client.ui.directory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.bus.RxBus;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryAdapter extends BaseAdapter{

    public DirectoryAdapter(@NonNull Context context, @NonNull RxBus busEvent) {
        super(context, busEvent);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
