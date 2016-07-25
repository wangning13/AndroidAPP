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
import com.akari.quark.entity.questionDetail.Answer;
import com.akari.quark.entity.questionDetail.Message;
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

//import okhttp3.Request;

/**
 * Created by motoon on 2016/5/12.
 */
public class QuestionDetailRecycleViewAdapter extends RecyclerView.Adapter<QuestionDetailRecycleViewAdapter.MyViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private static List<Answer> answerList = new ArrayList<Answer>();
    private static List<Answer> answerList2 = new ArrayList<Answer>();
    //    private OnItemClickListener mListener;
    private Message message;
    private com.akari.quark.entity.questionDetailAnswer.Message message1;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public QuestionDetailRecycleViewAdapter(Context context, Message message, com.akari.quark.entity.questionDetailAnswer.Message message1) {
        this.mContext = context;
        this.message = message;
        this.message1 = message1;
        if (message != null){
            answerList = message.getAnswers();
        }
        if (message1 != null){
            answerList2 = message1.getAnswers();
            answerList.addAll(answerList2);
        }
        mLayoutInflater = LayoutInflater.from(context);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    //    public void setOnItemClickListener(OnItemClickListener listener)
//    {
//        this.mListener = listener;
//    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if (mHeaderView != null && position == 0) {
            return new MyViewHolder(mHeaderView);

        } else {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
            return new MyViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (mHeaderView != null){
            if (position == 0) {
                holder.questionTitle.setText(message.getTitle());
                holder.content.setText(message.getContent());
                holder.focusNum.setText(message.getFocusNum() + "人关注");
                holder.answerNum.setText(message.getAnswerNum() + "人回答");
                for (int i = 0; i < message.getTopics().size(); i++) {
                    if (i != message.getTopics().size() - 1) {
                        holder.topics.setText(message.getTopics().get(i) + "·");
                    } else {
                        holder.topics.setText(message.getTopics().get(i));
                    }
                }
            } else {
                holder.context.setText(answerList.get(position - 1).getContent());
                holder.username.setText(answerList.get(position - 1).getUser().getName());
                holder.introduction.setText(answerList.get(position - 1).getUser().getIntroduction());
                holder.praiseNum.setText(answerList.get(position - 1).getPraiseNum() + "");
                final Long answerId = answerList.get(position - 1).getId();
                holder.context.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, AnswerDetailActivity.class);
                        intent.putExtra("answerId", String.valueOf(answerId));
                        intent.putExtra("questionTitle", message.getTitle());
                        mContext.startActivity(intent);
                    }
                });
            }
        }else {
            holder.context.setText(answerList.get(position).getContent());
            holder.username.setText(answerList.get(position).getUser().getName());
            holder.introduction.setText(answerList.get(position).getUser().getIntroduction());
            holder.praiseNum.setText(answerList.get(position).getPraiseNum() + "");
            final Long answerId = answerList.get(position).getId();
            holder.context.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AnswerDetailActivity.class);
                    intent.putExtra("answerId", String.valueOf(answerId));
                    intent.putExtra("questionTitle", message.getTitle());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? answerList2.size() : answerList.size() + 1;
    }

    /**
     * 批量增加
     */
    public void addItems(List<Answer> items) {
        if (items == null)
            return;
        this.answerList.addAll(0, items);
        this.notifyItemRangeInserted(0, items.size());
    }

    public interface OnItemClickListener {
        public void OnItemClick(View view, String data);
    }

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {
        public ViewHolderHeader(View itemView) {
            super(itemView);
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle;
        TextView content;
        TextView focusNum;
        TextView answerNum;
        TextView topics;

        CardView cardView;
        TextView context;
        TextView username;
        CircleImageView imageView;
        TextView introduction;
        TextView praiseNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                questionTitle = (TextView) itemView.findViewById(R.id.quetion_detail_title);
                content = (TextView) itemView.findViewById(R.id.content);
                focusNum = (TextView) itemView.findViewById(R.id.focus_num);
                answerNum = (TextView) itemView.findViewById(R.id.answer_num);
                topics = (TextView) itemView.findViewById(R.id.topic);

            } else {
                cardView = (CardView) itemView.findViewById(R.id.answer_card);
                context = (TextView) itemView.findViewById((R.id.answer_content1));
                username = (TextView) cardView.findViewById(R.id.username);
                imageView = (CircleImageView) cardView.findViewById(R.id.image_view);
                introduction = (TextView) cardView.findViewById(R.id.introduction);
                praiseNum = (TextView) cardView.findViewById(R.id.item_count);

            }

        }

        public CardView getmCardView() {
            return cardView;
        }

        ;
    }

}
