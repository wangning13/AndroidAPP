package com.akari.quark.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akari.quark.R;
import com.akari.quark.ui.activity.QuestionDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Akari on 2016/5/14.
 */
public class AnswerRecyclerViewAdapter extends RecyclerViewAdapter<AnswerRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<Integer> mInt = new ArrayList<>();

    public AnswerRecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_answer_main, parent, false));
    }

    /**
     * onBindViewHolder(NormalViewHolder holder, int position)
     * 专门用来绑定ViewHolder里的控件和数据源中position位置的数据。
     */
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        holder.item_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,QuestionDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInt == null ? 0 : mInt.size();
    }

    @Override
    public void add(int position) {
        mInt.add(position, 1);
        notifyItemInserted(position);
    }

    @Override
    public void remove(int position) {
        mInt.remove(position);
        notifyItemRemoved(position);
    }

    @Override
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

    @Override
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
        TextView item_title;
        TextView item_content;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.answer_card_view);
            item_title = (TextView) mCardView.findViewById(R.id.answer_item_title);
//            item_content = (TextView) mCardView.findViewById(R.id.item_content);
        }

        public CardView getmCardView() {
            return mCardView;
        }
    }
}
