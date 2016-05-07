package com.akari.quark.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akari.quark.R;

import java.util.ArrayList;

/**
 * Created by Akari on 2016/5/5.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<Integer> mInt = new ArrayList<>();

    public RecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_main, parent, false));
    }

    /**
     * onBindViewHolder(NormalViewHolder holder, int position)
     * 专门用来绑定ViewHolder里的控件和数据源中position位置的数据。
     */
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "蛤蛤", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mInt == null ? 0 : mInt.size();
    }

    public void add(int position) {
        mInt.add(position, 1);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mInt.remove(position);
        notifyItemRemoved(position);
    }

    public int loadMore() {
        int pos = mInt.size();
        mInt.add(mInt.size(), 1);
        mInt.add(mInt.size(), 1);
        mInt.add(mInt.size(), 1);
        mInt.add(mInt.size(), 1);
        mInt.add(mInt.size(), 1);
        notifyItemRangeInserted(mInt.size() - 5, mInt.size());
        return pos;
    }

    public void refresh() {
        mInt.add(0, 1);
        mInt.add(0, 1);
        mInt.add(0, 1);
        mInt.add(0, 1);
        mInt.add(0, 1);
        notifyItemRangeInserted(0, 5);
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
