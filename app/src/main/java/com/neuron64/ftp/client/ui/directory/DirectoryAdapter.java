package com.neuron64.ftp.client.ui.directory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import butterknife.BindView;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryAdapter extends BaseAdapter<FileSystemDirectory>{

    public DirectoryAdapter(@NonNull Context context, @NonNull RxBus busEvent) {
        super(context, busEvent);
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileHolder(inflate(R.layout.item_file, parent));
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
