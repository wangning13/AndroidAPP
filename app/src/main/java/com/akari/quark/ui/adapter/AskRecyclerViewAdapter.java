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
import com.akari.quark.entity.asksInMain.AsksInMainMessage;
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.activity.QuestionDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akari on 2016/5/5.
 */
public class AskRecyclerViewAdapter extends RecyclerViewAdapter<AskRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<AsksInMainMessage> mData;

    public AskRecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        mData=new ArrayList<AsksInMainMessage>();
        setHasStableIds(true);
    }

    public void setDataSource(List<AsksInMainMessage> data) {
        mData = data;

        notifyDataSetChanged();
    }

    public void addDataSource(List<AsksInMainMessage> data) {
        int pre=mData.size();

        mData.addAll(data);

        notifyItemRangeChanged(pre,data.size());
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
        holder.setListener(mContext);
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
    public void add(int position) {
//        mData.add(position, 1);
//        notifyItemInserted(position);
    }

    @Override
    public void remove(int position) {
//        mData.remove(position);
//        notifyItemRemoved(position);
    }

    @Override
    public int loadMore() {
//        mPage++;
//        int preSize= mData.size();
//        load();
//        notifyItemRangeChanged(preSize, mData.size());
//        return preSize+1;
        return 0;
    }

    @Override
    public void refresh() {
//        mPage=2;
//        int preSize= mData.size();
//        mData.clear();
//        notifyItemRangeRemoved(0,preSize);
//        load();
//        notifyItemRangeInserted(0, mData.size());
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

        public void setListener(final Context context){
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,QuestionDetailActivity.class);
                    context.startActivity(intent);
                }
            });
            mContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AnswerDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

//    private void load(){
//        String url = OkHttpManager.API_GET_ALL_ASK_QUESTIONS;
//        String urlDetail = OkHttpManager.attachHttpGetParam(url,"page",String.valueOf(mPage));
//        OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, IOException e) {
//                Toast.makeText(mContext,"无法访问",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                AsksInMain asksInMain = GsonUtil.GsonToBean(result,AsksInMain.class);
//                List<AsksInMainMessage> messageList = asksInMain.getMessage();
//                mData.addAll(messageList);
//                Toast.makeText(mContext,"新增"+ mData.size(),Toast.LENGTH_SHORT).show();
//            }
//        };
//        OkHttpManager.getAsync(urlDetail,dataCallBack,OkHttpManager.X_ACCESS_TOKEN,OkHttpManager.TEMP_X_ACCESS_TOKEN);
//    }
}