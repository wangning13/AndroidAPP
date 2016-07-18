package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.akari.quark.R;
import com.akari.quark.entity.follow.FollowMessage;
import com.akari.quark.ui.adapter.FollowRecyclerViewAdapter;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;
import com.akari.quark.ui.listener.OnVerticalScrollListener;
import com.akari.quark.ui.view.DividerLine;
import com.hippo.refreshlayout.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class FollowActivity extends AppCompatActivity implements RefreshLayout.OnRefreshListener {
    private static final String TAG = FollowActivity.class.getSimpleName();
    public static final FollowType UNKNOWN = FollowType.Unknown;

    private int mPage;
    private long mUserID;

    private Context mContext;
    private NewRecyclerViewAdapter<FollowRecyclerViewAdapter.NormalViewHolder> mAdapter;
    private Toolbar mToolbar;
    private RefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private FollowType mFollowType;

    enum FollowType {
        Followee, Follower, Unknown
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followee_follower);

        mContext = FollowActivity.this;
        mPage = 1;
        mUserID = getIntent().getLongExtra("userID", 0);

        mFollowType = FollowType.valueOf(getIntent().getStringExtra("followType"));

        mToolbar = (Toolbar) findViewById(R.id.follow_toolbar);
        assert mToolbar != null;
        switch (mFollowType){
            case Followee:
                mToolbar.setTitle("我关注的人");
                break;
            case Follower:
                mToolbar.setTitle("关注我的人");
                break;
            default:
                break;
        }
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mLayout = (RefreshLayout) findViewById(R.id.follow_refresh_layout);
        assert mLayout != null;
        mLayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.follow_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter = new FollowRecyclerViewAdapter(mContext));//TODO 以后要分类
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

        List<FollowMessage> followMessageList = new ArrayList<>();
        FollowMessage followMessage = new FollowMessage();
        followMessage.setId(1024);
        followMessage.setIntroduction("一个未解之谜，巴拉拉小魔仙");
        followMessage.setName("彭定康");
        for (int i = 0; i < 10; i++) {
            followMessageList.add(followMessage);
        }
        mAdapter.setDataSource(followMessageList);

        mRecyclerView.smoothScrollToPosition(0);

        mLayout.setHeaderRefreshing(false);
    }

    @Override
    public void onFooterRefresh() {
//        mPage++;
        mLayout.setFooterRefreshing(false);
    }
}
