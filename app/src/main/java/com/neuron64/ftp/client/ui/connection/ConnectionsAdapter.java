package com.neuron64.ftp.client.ui.connection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseAdapter;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.UserConnection;

import butterknife.BindView;

/**
 * Created by Neuron on 17.09.2017.
 */

public class ConnectionsAdapter extends BaseAdapter<UserConnection> {

    public ConnectionsAdapter(@NonNull Context context, @NonNull RxBus busEvent) {
        super(context, busEvent);
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConnectionViewHolder(inflate(R.layout.item_connection, parent));
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBind();
    }

    public class ConnectionViewHolder extends GenericViewHolder {

        @BindView(R.id.tv_name) TextView tvName;

        @BindView(R.id.tv_host) TextView tvHost;

        @BindView(R.id.tv_user_name) TextView tvUserName;

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
    }

    private void setTextView(TextView textView, String text){
        if(text != null){
            textView.setText(text);
        }else{
            textView.setText(null);
        }
    }
}
