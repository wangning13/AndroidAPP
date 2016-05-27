package com.akari.quark.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akari.quark.R;
import com.akari.quark.entity.answersInMain.AnswersInMainMessage;
import com.akari.quark.ui.activity.QuestionDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Akari on 2016/5/14.
 */
public class AnswerRecyclerViewAdapter extends NewRecyclerViewAdapter<AnswerRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<AnswersInMainMessage> mData;

    public AnswerRecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        setHasStableIds(true);
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
        final AnswersInMainMessage answer = mData.get(position);
        holder.fillData(answer);
        String questionId = String.valueOf(answer.getId());
        holder.setListener(mContext,questionId);
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

        mData.addAll((List<AnswersInMainMessage>) list);

        notifyDataSetChanged();

        return pre;
    }

    @Override
    public void setDataSource(List<?> list) {
        mData = (List<AnswersInMainMessage>) list;

        notifyDataSetChanged();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private final CardView mCardView;
        private final TextView mTitle;
        private final TextView mAttentionCount;
        private final TextView mAnswerCount;
        private final TextView mTopic;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.answer_card_view);
            mTitle = (TextView) mCardView.findViewById(R.id.answer_item_title);
            mAttentionCount = (TextView) mCardView.findViewById(R.id.attention_count);
            mAnswerCount = (TextView) mCardView.findViewById(R.id.answer_count);
            mTopic = (TextView) mCardView.findViewById(R.id.answer_item_tag);
        }

        public void fillData(AnswersInMainMessage ask) {
            mCardView.setVisibility(View.VISIBLE);
            mTitle.setText(ask.getTitle());
            mAttentionCount.setText(ask.getFocusNum()+"人关注");
            mAnswerCount.setText(ask.getAnswerNum()+"个回答");
            if(ask.getTopics()!=null){
                if(!ask.getTopics().isEmpty())
                    mTopic.setText(ask.getTopics().get(0));
            }
        }

        public void setListener(final Context context, final String questionId) {
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, QuestionDetailActivity.class);
                    intent.putExtra("questionId",questionId);
                    context.startActivity(intent);
                }
            });
        }
    }
}
