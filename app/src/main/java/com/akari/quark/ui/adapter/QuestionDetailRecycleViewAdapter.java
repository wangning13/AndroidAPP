package com.akari.quark.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import com.akari.quark.R;

/**
 * Created by motoon on 2016/5/12.
 */
public class QuestionDetailRecycleViewAdapter extends RecyclerView.Adapter<QuestionDetailRecycleViewAdapter.ViewHolder>{

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        final View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_headerview,parent,false);

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
        return new ViewHolder(itemView);
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
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String s = datas.get(position);
        holder.bindData(s);
        holder.itemView.setTag(s);
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

    class ViewHolderHeader extends RecyclerView.ViewHolder{
        public ViewHolderHeader(View itemView){
            super(itemView);
            final Button button = (Button)itemView.findViewById(R.id.concern_button);
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (button.getText().equals("关注")){
                            System.out.println("有木有关注");
                            button.setText("已关注");
                            button.setBackgroundColor(Color.parseColor("#D1D1D1"));
                        }
                        if (button.getText().equals("已关注")){
                            button.setText("关注");
                            System.out.println("到死有木有关注");
                            button.setBackgroundColor(Color.parseColor("#00A162"));
                        }
                    }
                });
            }
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.answer_card_view);
        }
        public void bindData(String s)
        {
//            if (s != null)
////                mContent.setText(s);
        }
        interface OnItemClickListener {
            void onItemClick(int position, String data);
        }
    }

}
