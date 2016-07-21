package com.akari.quark.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akari.quark.R;
import com.akari.quark.entity.comment.CommentMessage;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;
import com.akari.quark.util.DateUtil;

import java.util.List;

/**
 * Created by Akari on 16/7/13.
 */
public class CommentRecyclerViewAdapter extends NewRecyclerViewAdapter<CommentRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<CommentMessage> mData;

    public CommentRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_comment, parent, false));
    }

    /**
     * onBindViewHolder(NormalViewHolder holder, int position)
     * 专门用来绑定ViewHolder里的控件和数据源中position位置的数据。
     */
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        final CommentMessage comment = mData.get(position);
        holder.fillData(comment);
//        holder.setListener(mContext, comment);
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

        mData.addAll((List<CommentMessage>) list);

        notifyItemRangeInserted(pre, list.size());

        return pre;
    }

    @Override
    public void setDataSource(List list) {
        mData = (List<CommentMessage>) list;

        notifyDataSetChanged();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private final TextView mUsername;
        private final TextView mContent;
        private final TextView mTime;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mUsername = (TextView) itemView.findViewById(R.id.username_tv);
            mTime = (TextView) itemView.findViewById(R.id.time_tv);
        }

        public void fillData(CommentMessage comment) {
            mUsername.setText(comment.getReplyer_name());
            mContent.setText(comment.getContent());
            mTime.setText(DateUtil.formatUnixTime(comment.getCreate_time()));
        }
    }
}
