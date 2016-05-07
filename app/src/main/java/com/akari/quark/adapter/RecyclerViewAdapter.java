package com.akari.quark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.activity.QuestionDetailActivity;

/**
 * Created by Akari on 2016/5/5.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public RecyclerViewAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);

    }


    public  static class NormalViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.card_view);
        }



    }
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_main,parent,false));
    }

    /**
     * onBindViewHolder(NormalViewHolder holder, int position)
     * 专门用来绑定ViewHolder里的控件和数据源中position位置的数据。
     */
    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"蛤蛤",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
//                startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
