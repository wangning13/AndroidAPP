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
import com.akari.quark.entity.follow.FollowMessage;
import com.akari.quark.entity.questionDetail.QuestionDetail;
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.activity.FieldDetailActivity;
import com.akari.quark.ui.activity.QuestionDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Akari on 16/7/13.
 */
public class FollowRecyclerViewAdapter extends NewRecyclerViewAdapter<FollowRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<FollowMessage> mData;
    private Context mContext;

    public FollowRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_user, parent, false));
    }

    /**
     * onBindViewHolder(NormalViewHolder holder, int position)
     * 专门用来绑定ViewHolder里的控件和数据源中position位置的数据。
     */
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        final FollowMessage follow = mData.get(position);
        holder.fillData(follow);
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FieldDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mData == null ? RecyclerView.NO_ID : mData.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int addDataSource(List list) {
        int pre = mData.size();

        mData.addAll((List<FollowMessage>) list);

        notifyItemRangeInserted(pre, list.size());

        return pre;
    }

    @Override
    public void setDataSource(List list) {
        mData = (List<FollowMessage>) list;

        notifyDataSetChanged();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private final View mview;
        private final TextView mUsername;
        private final TextView mIntroduction;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mview = (View) itemView.findViewById(R.id.item_user);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mIntroduction = (TextView) itemView.findViewById(R.id.introduction);
        }

        public void fillData(FollowMessage followMessage) {
            mUsername.setText(followMessage.getName());
            mIntroduction.setText(followMessage.getIntroduction());
        }
    }
}
