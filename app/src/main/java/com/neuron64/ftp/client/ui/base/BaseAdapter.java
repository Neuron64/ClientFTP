package com.neuron64.ftp.client.ui.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.client.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Neuron on 17.09.2017.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter<T>.GenericViewHolder>{

    protected List<T> items;
    protected Context context;
    protected RxBus busEvent;
    private LayoutInflater inflater;

    public BaseAdapter(@NonNull Context context, @NonNull RxBus busEvent) {
        this.inflater = LayoutInflater.from(context);

        this.context = Preconditions.checkNotNull(context);
        this.busEvent = Preconditions.checkNotNull(busEvent);
        this.items = new ArrayList<>();
    }

    public abstract class GenericViewHolder extends RecyclerView.ViewHolder{
        public GenericViewHolder(View rootView) {
            super(rootView);
            ButterKnife.bind(GenericViewHolder.this, rootView);
        }

        public abstract void onBind();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected View inflate(@LayoutRes int id, ViewGroup container){
        return inflater.inflate(id,container,false);
    }

    public T at(int index) {
        return items.get(index);
    }

    public void setItems(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<T> items){
        int size = getItemCount();
        this.items.addAll(items);
        notifyItemRangeInserted(size, getItemCount());
    }

    public void addItem(T item){
        int size = getItemCount();
        this.items.add(item);
        notifyItemRangeInserted(size, getItemCount());
    }

    public void addItemToStart(T item){
        this.items.add(0, item);
        notifyItemInserted(0);
    }

    public T removeItem(int position){
        T object = items.remove(position);
        notifyItemRemoved(position);
        return object;
    }

    public void clearItems(){
        items.clear();
        notifyDataSetChanged();
    }
}
