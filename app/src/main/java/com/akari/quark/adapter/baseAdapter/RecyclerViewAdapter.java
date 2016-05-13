package com.akari.quark.adapter.baseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Akari on 16/5/14.
 */
public abstract class RecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public abstract void add(int position);

    public abstract void remove(int position);

    public abstract int loadMore();

    public abstract void refresh();

}
