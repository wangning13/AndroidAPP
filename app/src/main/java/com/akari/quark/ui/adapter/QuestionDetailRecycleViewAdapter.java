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
import com.akari.quark.entity.Answer;
import com.akari.quark.entity.Message;
import com.akari.quark.entity.QuestinoDetail;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.view.CircleImageView;
import com.akari.quark.util.GsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

//import okhttp3.Request;

/**
 * Created by motoon on 2016/5/12.
 */
public class QuestionDetailRecycleViewAdapter extends RecyclerView.Adapter<QuestionDetailRecycleViewAdapter.MyViewHolder> {
    private static final String API_QUESTION_DETAIL = "/api/question/detail";
    private static final String X_ACCESS_TOKEN="x-access-token";
    private static final String TEMP_X_ACCESS_TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0MTkwOTc1NDYxfQ.5ejSZACMPlz3KXgQmBgINYYfgxULmEx2zVf-19TN34E";

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private List<Answer> answerList = new ArrayList<Answer>();
    private OnItemClickListener mListener;
    private Message message;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }
    public QuestionDetailRecycleViewAdapter(Context context)
    {
        this.mContext = context;
        mLayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mListener != null)
                {
                    mListener.OnItemClick(v, (String) itemView.getTag());
                }
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }
//    public void addDatas(List<String> mdatas) {
//        datas.addAll(mdatas);
//        notifyDataSetChanged();
//    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        int question_id = 1;

        String url = OkHttpManager.BASE_URL+API_QUESTION_DETAIL;
        String urlDetail = OkHttpManager.attachHttpGetParam(url,"question_id",String.valueOf(question_id));
        OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(mContext,"无法访问",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                QuestinoDetail questinoDetail = GsonUtil.GsonToBean(result,QuestinoDetail.class);
                Message message = questinoDetail.getMessage();
                String title = message.getTitle();
                String content = message.getContent();
                String focusNum = String.valueOf(message.getFocusNum())+"人关注";
                String answerNum =  String.valueOf(message.getAnswerNum())+"人回答";
                List<String> topics = message.getTopics();
                answerList = message.getAnswers();
                if(position==0){
                    holder.questionTitle.setText(answerList.size()+"个数据");
                    holder.content.setText(content);
                    holder.focusNum.setText(focusNum);
                    holder.answerNum.setText(answerNum);
                    for (int i=0;i<topics.size();i++){
                        if(i!=topics.size()-1){
                            holder.topics.setText(topics.get(i) +"·");
                        }else {
                            holder.topics.setText(topics.get(i));
                        }
                    }
                }else{
                    notifyDataSetChanged();
                    holder.context.setText(answerList.get(position-1).getContent());
                    holder.username.setText(answerList.get(position-1).getUser().getName());
                    holder.introduction.setText(answerList.get(position-1).getUser().getIntroduction());
                }

            }
        };
        OkHttpManager.getAsync(urlDetail,dataCallBack,X_ACCESS_TOKEN,TEMP_X_ACCESS_TOKEN);

        if(position!=0){
            holder.context.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,AnswerDetailActivity.class);
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
    public int getItemCount()
    {
        return mHeaderView == null ? answerList.size() : answerList.size()+1 ;
    }
    /**
     * 批量增加
     * */
    public void addItems(List<Answer> items)
    {
        if (items == null)
            return;
        this.answerList.addAll(0, items);
        this.notifyItemRangeInserted(0, items.size());
    }
    public interface OnItemClickListener
    {
        public void OnItemClick(View view,String data);
    }

    public static class ViewHolderHeader extends RecyclerView.ViewHolder{
        public ViewHolderHeader(View itemView){
            super(itemView);
        }

    }


    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
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
        public MyViewHolder(View itemView)
        {
            super(itemView);
            if(itemView == mHeaderView){
                questionTitle = (TextView) itemView.findViewById(R.id.quetion_detail_title);
                content = (TextView) itemView.findViewById(R.id.content);
                focusNum = (TextView) itemView.findViewById(R.id.focus_num);
                answerNum = (TextView) itemView.findViewById(R.id.answer_num);
                topics = (TextView) itemView.findViewById(R.id.topic);

            }else {
                cardView = (CardView) itemView.findViewById(R.id.answer_card);
                context = (TextView) itemView.findViewById((R.id.answer_content1));
                username = (TextView) cardView.findViewById(R.id.username);
                imageView = (CircleImageView) cardView.findViewById(R.id.image_view);
                introduction = (TextView) cardView.findViewById(R.id.introduction);

            }

        }
        public CardView getmCardView() {
            return cardView;
        };
    }

}
