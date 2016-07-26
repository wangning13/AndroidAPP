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
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.BaseRecyclerAdapter;
import com.akari.quark.ui.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

//import okhttp3.Request;

/**
 * Created by motoon on 2016/5/12.
 */
public class QuestionDetailRecycleViewAdapter extends BaseRecyclerAdapter<Answer> {
    private Context mContext;
    private String title;

    public QuestionDetailRecycleViewAdapter(Context context,String title) {
        this.mContext = context;
        this.title = title;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, Answer data) {
        ((MyViewHolder)viewHolder).context.setText(data.getContent());
        ((MyViewHolder)viewHolder).username.setText(data.getUser().getName());
        ((MyViewHolder)viewHolder).introduction.setText(data.getUser().getIntroduction());
        ((MyViewHolder)viewHolder).praiseNum.setText(data.getPraiseNum() + "");
        final Long answerId = data.getId();
        ((MyViewHolder)viewHolder).context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AnswerDetailActivity.class);
                intent.putExtra("answerId", String.valueOf(answerId));
                intent.putExtra("questionTitle", title);
                mContext.startActivity(intent);
            }
        });
    }

    public class MyViewHolder extends BaseRecyclerAdapter.Holder {
        CardView cardView;
        TextView context;
        TextView username;
        CircleImageView imageView;
        TextView introduction;
        TextView praiseNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.answer_card);
            context = (TextView) itemView.findViewById((R.id.answer_content1));
            username = (TextView) cardView.findViewById(R.id.username);
            imageView = (CircleImageView) cardView.findViewById(R.id.image_view);
            introduction = (TextView) cardView.findViewById(R.id.introduction);
            praiseNum = (TextView) cardView.findViewById(R.id.item_count);
        }
    }

}
