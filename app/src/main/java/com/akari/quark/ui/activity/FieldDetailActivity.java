package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.Information;
import com.akari.quark.entity.questionDetail.Message;
import com.akari.quark.entity.questionDetail.QuestionDetail;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.ui.adapter.FieldDetailRecyclerViewAdapter;
import com.akari.quark.ui.adapter.QuestionDetailRecycleViewAdapter;
import com.akari.quark.util.GsonUtil;
import com.hippo.refreshlayout.RefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Request;

/**
 * Created by motoon on 2016/7/26.
 */
public class FieldDetailActivity extends AppCompatActivity implements RefreshLayout.OnRefreshListener{
    public static Handler sHandler = new Handler();
    private static Context context;
    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshlayout;
    private LinearLayoutManager mLinearLayoutManager;
    private View header;
    private List<String> answerList = new ArrayList<>();
    private FieldDetailRecyclerViewAdapter mAdapter;
    private int mPage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_detail);
        context = FieldDetailActivity.this;
        mRecyclerView = (RecyclerView) findViewById(R.id.field_answer_list);
        setRefreshLayout();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshlayout.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //每个item高度一致，可设置为true，提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
        toolbar.setTitle("领域详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setHeader();
    }

    
    private void setHeader() {
        header = LayoutInflater.from(context).inflate(R.layout.field_detail_headerview, mRecyclerView, false);
        for (int i=0;i<10;i++){
            answerList.add("hah");
        }
        mAdapter = new FieldDetailRecyclerViewAdapter();
        mAdapter.addDatas(answerList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(header);
    }

    @Override
    public void onHeaderRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPage = 1;
                mRecyclerView.scrollToPosition(0);
                mRefreshlayout.setHeaderRefreshing(false);
            }
        }, 100);

    }

    @Override
    public void onFooterRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPage++;
                mRefreshlayout.setFooterRefreshing(false);
                List<String> lists = new ArrayList<String>();
                for (int i=0;i<10;i++){
                    lists.add("hdh");
                }
                mAdapter.addDatas(lists);
            }
        }, 100);
    }

    private void setRefreshLayout() {
        mRefreshlayout = (RefreshLayout) findViewById(R.id.swipe_container);
        mRefreshlayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshlayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
