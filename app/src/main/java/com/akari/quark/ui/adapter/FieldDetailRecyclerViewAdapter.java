package com.akari.quark.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akari.quark.R;
import com.akari.quark.entity.questionDetail.Answer;
import com.akari.quark.ui.adapter.baseAdapter.BaseRecyclerAdapter;
import com.akari.quark.ui.view.CircleImageView;

/**
 * Created by motoon on 2016/7/26.
 */
public class FieldDetailRecyclerViewAdapter extends BaseRecyclerAdapter<String> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_field, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, String data) {

    }

    public class MyViewHolder extends BaseRecyclerAdapter.Holder {
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
