package com.neuron64.ftp.client.ui.directory.ftp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import butterknife.BindView;

/**
 * Created by yks-11 on 10/17/17.
 */

public class DirectoryFtpAdapter extends BaseAdapter<FileSystemDirectory> {

    public DirectoryFtpAdapter(@NonNull Context context, @NonNull RxBus busEvent) {
        super(context, busEvent);
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DirectoryFtpAdapter.FileHolder(inflate(R.layout.item_file, parent));
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBind();
    }

    public class FileHolder extends GenericViewHolder{

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public FileHolder(View rootView) {
            super(rootView);
        }

        @Override
        public void onBind() {
            FileSystemDirectory fileSystemDirectory = at(getAdapterPosition());
            tvTitle.setText(fileSystemDirectory.getTitle());
        }
    }
}