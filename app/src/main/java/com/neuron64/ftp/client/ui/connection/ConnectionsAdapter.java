package com.neuron64.ftp.client.ui.connection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.UserConnection;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Neuron on 17.09.2017.
 */

public class ConnectionsAdapter extends BaseAdapter<UserConnection> {

    public interface OnItemClickListener {
        void onDeleteConnection(UserConnection connection, int positionAdapter);
        void onChangeConnection(UserConnection connection, int positionAdapter);
        void onTestConnection(UserConnection connection, int positionAdapter);
    }

    @NonNull
    private OnItemClickListener onItemClickListener;

    public ConnectionsAdapter(@NonNull Context context, @NonNull RxBus busEvent, @NonNull OnItemClickListener onItemClickListener) {
        super(context, busEvent);
        this.onItemClickListener = Preconditions.checkNotNull(onItemClickListener);
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConnectionViewHolder(inflate(R.layout.item_connection, parent));
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBind();
    }

    public class ConnectionViewHolder extends GenericViewHolder implements PopupMenu.OnMenuItemClickListener {

        @BindView(R.id.tv_name) TextView tvName;

        @BindView(R.id.tv_host) TextView tvHost;

        @BindView(R.id.tv_user_name) TextView tvUserName;

        @BindView(R.id.ib_menu_pop)  ImageButton ibMenuPop;

        public ConnectionViewHolder(View rootView) {
            super(rootView);
        }

        @Override
        public void onBind() {
            UserConnection connection = at(getAdapterPosition());
            setTextView(tvName, connection.getNameConnection());
            setTextView(tvUserName, connection.getUserName());
            setTextView(tvHost, connection.getHost());
        }

        @OnClick(R.id.ib_menu_pop)
        public void onClickPopMenu(View view){
            PopupMenu popup = new PopupMenu(context, view);

            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.menu_select_connection);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            UserConnection connection = at(getAdapterPosition());

            switch (item.getItemId()){
                case R.id.action_change:{
                    onItemClickListener.onChangeConnection(connection, getAdapterPosition());
                    break;
                }
                case R.id.action_delete:{
                    onItemClickListener.onDeleteConnection(connection, getAdapterPosition());
                    break;
                }
                case R.id.action_test:{
                    onItemClickListener.onTestConnection(connection, getAdapterPosition());
                    break;
                }
            }
            return false;
        }
    }

    @Override
    public void onViewRecycled(GenericViewHolder holder) {
        super.onViewRecycled(holder);
    }

    private void setTextView(TextView textView, String text){
        if(text != null){
            textView.setText(text);
        }else{
            textView.setText(null);
        }
    }
}
