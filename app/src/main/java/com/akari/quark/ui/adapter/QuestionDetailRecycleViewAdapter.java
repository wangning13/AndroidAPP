package com.akari.quark.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akari.quark.R;

import java.util.List;

/**
 * Created by motoon on 2016/5/12.
 */
public class QuestionDetailRecycleViewAdapter extends RecyclerView.Adapter<QuestionDetailRecycleViewAdapter.MyViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private List<String> datas = null;
    private OnItemClickListener mListener;
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
    public QuestionDetailRecycleViewAdapter(List<String> datas)
    {
        this.datas = datas;
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
    public void addDatas(List<String> mdatas) {
        datas.addAll(mdatas);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
//        String s = datas.get(position);
//        holder.bindData(s);
//        holder.itemView.setTag(s);
        if(position!=0)
            holder.username.setText("djshf");
    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount()
    {
        return mHeaderView == null ? datas.size() : datas.size() ;
    }
    /**
     * 批量增加
     * */
    public void addItems(List<String> items)
    {
        if (items == null)
            return;
        this.datas.addAll(0, items);
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
        CardView cardView;
        TextView username;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            if(itemView == mHeaderView) return;
            cardView = (CardView) itemView.findViewById(R.id.answer_card);
            username = (TextView) cardView.findViewById(R.id.username);
        }
        public CardView getmCardView() {
            return cardView;
        };
    }

}
