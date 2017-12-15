package com.neuron64.ftp.client.ui.directory.ftp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.FileInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yks-11 on 10/17/17.
 */

public class DirectoryFtpAdapter extends BaseAdapter<FileInfo> {

    public interface OnItemClickListener {
        void onClickDeleteFile(FileInfo fileInfo, int positionAdapter);
        void onClickItem(FileInfo fileInfo, int positionAdapter);
        void onClickChangeFile(FileInfo fileInfo, int positionAdapter);
        void onClickMoveFile(FileInfo fileInfo, int positionAdapter);
        void onClickRenameFile(FileInfo fileInfo, int positionAdapter);
        void onClickDownloadFile(FileInfo fileInfo, int positionAdapter);
    }

    private final OnItemClickListener listener;

    public DirectoryFtpAdapter(@NonNull Context context, @NonNull RxBus busEvent, @NonNull OnItemClickListener listener) {
        super(context, busEvent);
        this.listener = listener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DirectoryFtpAdapter.FileHolder(inflate(R.layout.item_file, parent));
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBind();
    }

    public class FileHolder extends GenericViewHolder implements PopupMenu.OnMenuItemClickListener{

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_is_directory)
        TextView tvIsDirectory;

        public FileHolder(View rootView) {
            super(rootView);
        }

        @Override
        public void onBind() {
            FileInfo fileSystemDirectory = at(getAdapterPosition());
            tvTitle.setText(fileSystemDirectory.getTitle());
            String isDirectory = fileSystemDirectory.isDirectory() ? context.getString(R.string.title_directory) : context.getString(R.string.title_file);
            tvIsDirectory.setText(isDirectory);
        }

        @OnClick({R.id.ll_root})
        void onClick(){
            int position = getAdapterPosition();
            listener.onClickItem(at(position), position);
        }

        @OnClick(R.id.ib_menu_pop)
        public void onClickPopMenu(View view) {
            PopupMenu popup = new PopupMenu(context, view);

            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.menu_select_file_system);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();

            FileInfo fileInfo = at(position);

            switch (item.getItemId()){
                case R.id.action_delete:{
                    listener.onClickDeleteFile(fileInfo, position);
                    return true;
                }
                case R.id.action_move:{
                    listener.onClickMoveFile(fileInfo, position);
                    return true;
                }
                case R.id.action_rename:{
                    listener.onClickRenameFile(fileInfo, position);
                    return true;
                }
                case R.id.action_change:{
                    listener.onClickChangeFile(fileInfo, position);
                    return true;
                }
                case R.id.action_download:{
                    listener.onClickDownloadFile(fileInfo, position);
                    return true;
                }
            }
            return false;
        }
    }
}