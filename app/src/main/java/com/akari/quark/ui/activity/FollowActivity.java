package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.comment.Comment;
import com.akari.quark.entity.comment.CommentResult;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.ui.adapter.CommentRecyclerViewAdapter;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;
import com.akari.quark.ui.helper.CommentHelper;
import com.akari.quark.ui.listener.OnVerticalScrollListener;
import com.akari.quark.ui.loader.AsyncTaskLoader;
import com.akari.quark.ui.loader.CommentListLoader;
import com.akari.quark.ui.tool.ErrorNotification;
import com.akari.quark.ui.view.DividerLine;
import com.akari.quark.util.GsonUtil;
import com.hippo.refreshlayout.RefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class FollowActivity extends AppCompatActivity implements RefreshLayout.OnRefreshListener {
    private static final String TAG = FollowActivity.class.getSimpleName();

    private int mPage;
    private long mUserID;

    private Context mContext;
    private NewRecyclerViewAdapter<CommentRecyclerViewAdapter.NormalViewHolder> mAdapter;
    private Toolbar mToolbar;
    private RefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private FollowType followType;

    enum FollowType {
        Followee, Follower
    }

    public FollowActivity(FollowType followType){
        this.followType = followType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mContext = FollowActivity.this;
        mPage = 1;
        mUserID = getIntent().getLongExtra("userID", 0);

        mToolbar = (Toolbar)findViewById(R.id.comment_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mLayout = (RefreshLayout) findViewById(R.id.comment_refresh_layout);
        mLayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.comment_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter = new CommentRecyclerViewAdapter(mContext));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerLine(DividerLine.VERTICAL, 2, 0xFFDDDDDD));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledToBottom() {
                mLayout.setFooterRefreshing(true);
                onFooterRefresh();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayout.setHeaderRefreshing(true);
                onHeaderRefresh();
            }
        }, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onHeaderRefresh() {
        mPage = 1;

        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onFooterRefresh() {
        mPage++;
    }
}
