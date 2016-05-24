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
import com.akari.quark.entity.asksInMain.AsksInMain;
import com.akari.quark.entity.asksInMain.AsksInMainMessage;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.ui.activity.AnswerDetailActivity;
import com.akari.quark.ui.activity.QuestionDetailActivity;
import com.akari.quark.ui.adapter.baseAdapter.RecyclerViewAdapter;
import com.akari.quark.util.GsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Akari on 2016/5/5.
 */
public class AskRecyclerViewAdapter extends RecyclerViewAdapter<AskRecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<AsksInMainMessage> mMessage = new ArrayList<AsksInMainMessage>();
    private int mPage=1;

    public AskRecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
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
        holder.item_title.setText(mMessage.get(position).getTitle());
        holder.item_content.setText(mMessage.get(position).getBestAnswer().getContent());

        holder.item_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,QuestionDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.item_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,AnswerDetailActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMessage == null ? 0 : mMessage.size();
    }

    @Override
    public void add(int position) {
//        mMessage.add(position, 1);
//        notifyItemInserted(position);
    }

    @Override
    public void remove(int position) {
//        mMessage.remove(position);
//        notifyItemRemoved(position);
    }

    @Override
    public int loadMore() {
        mPage++;
        int preSize=mMessage.size();
        load();
        notifyItemRangeChanged(preSize,mMessage.size());
        return preSize+1;
    }

    @Override
    public void refresh() {
        mPage=2;
        int preSize=mMessage.size();
        mMessage.clear();
        notifyItemRangeRemoved(0,preSize);
        load();
        notifyItemRangeInserted(0,mMessage.size());
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView item_title;
        TextView item_content;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            item_title = (TextView) mCardView.findViewById(R.id.item_title);
            item_content = (TextView) mCardView.findViewById(R.id.item_content);
        }

        public CardView getmCardView() {
            return mCardView;
        }
    }

    private void load(){
        String url = OkHttpManager.API_GET_ALL_ASK_QUESTIONS;
        String urlDetail = OkHttpManager.attachHttpGetParam(url,"page",String.valueOf(mPage));
        OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(mContext,"无法访问",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                AsksInMain asksInMain = GsonUtil.GsonToBean(result,AsksInMain.class);
                List<AsksInMainMessage> messageList = asksInMain.getMessage();
                mMessage.addAll(messageList);
                Toast.makeText(mContext,"新增"+mMessage.size(),Toast.LENGTH_SHORT).show();
            }
        };
        OkHttpManager.getAsync(urlDetail,dataCallBack,OkHttpManager.X_ACCESS_TOKEN,OkHttpManager.TEMP_X_ACCESS_TOKEN);
    }
}