package com.akari.quark.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.asksInMain.AsksInMainMessage;
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.activity.QuestionDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Akari on 2016/5/5.
 */
public class AskRecyclerViewAdapter extends NewRecyclerViewAdapter<AskRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<AsksInMainMessage> mData;

    public AskRecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_ask_main, parent, false));
    }

    /**
     * onBindViewHolder(NormalViewHolder holder, int position)
     * 专门用来绑定ViewHolder里的控件和数据源中position位置的数据。
     */
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        final AsksInMainMessage ask=mData.get(position);
        holder.fillData(ask);
        String questionId = String.valueOf(ask.getId());
        String answerId = String.valueOf(ask.getBestAnswer().getAnswererId());
        String questionTitle = ask.getTitle();
        holder.setListener(mContext,questionId,answerId,questionTitle);
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
    public int addDataSource(List<?> list) {
        int pre = mData.size();

        mData.addAll((List<AsksInMainMessage>) list);

        notifyItemRangeInserted(pre, list.size());

        return pre;
    }

    @Override
    public void setDataSource(List<?> list) {
        mData = (List<AsksInMainMessage>) list;

        notifyDataSetChanged();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private final CardView mCardView;
        private final TextView mTitle;
        private final TextView mPraise;
        private final TextView mContent;
        private final TextView mTopic;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitle = (TextView) mCardView.findViewById(R.id.item_title);
            mPraise = (TextView) mCardView.findViewById(R.id.item_count);
            mContent = (TextView) mCardView.findViewById(R.id.item_content);
            mTopic = (TextView) mCardView.findViewById(R.id.item_tag);
        }

        public void fillData(AsksInMainMessage ask) {
            mCardView.setVisibility(View.VISIBLE);
            mTitle.setText(ask.getTitle());
            mPraise.setText(ask.getBestAnswer().getPraiseNum()+"");
            mContent.setText(ask.getBestAnswer().getContent());
            mTopic.setText(ask.getTopics().get(0));
        }

        public void setListener(final Context context, final String questionId, final String answerId, final String questionTitle){
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,QuestionDetailActivity.class);
                    intent.putExtra("questionId",questionId);
                    context.startActivity(intent);
                }
            });
            if(answerId.equals(null)){
                mContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,answerId,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,AnswerDetailActivity.class);
                        intent.putExtra("answerId",answerId);
                        intent.putExtra("questionTitle",questionTitle);
                        context.startActivity(intent);
                    }
                });
            }

        }
    }
}