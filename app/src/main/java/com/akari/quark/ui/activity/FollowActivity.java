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
import com.akari.quark.entity.follow.Follow;
import com.akari.quark.ui.adapter.FollowRecyclerViewAdapter;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;
import com.akari.quark.ui.listener.OnVerticalScrollListener;
import com.akari.quark.ui.loader.AsyncTaskLoader;
import com.akari.quark.ui.loader.FollowListLoader;
import com.akari.quark.ui.tool.ErrorNotification;
import com.akari.quark.ui.view.DividerLine;
import com.hippo.refreshlayout.RefreshLayout;

public class FollowActivity extends AppCompatActivity implements RefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<AsyncTaskLoader.LoaderResult<?>> {
    private static final String TAG = FollowActivity.class.getSimpleName();

    private int mPage;
    private long mUserID;

    private Context mContext;
    private NewRecyclerViewAdapter<FollowRecyclerViewAdapter.NormalViewHolder> mAdapter;
    private Toolbar mToolbar;
    private RefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private FollowType mFollowType;

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
        switch (mFollowType) {
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
        getSupportLoaderManager().restartLoader(0, null, this);

        final Loader<?> loader = getSupportLoaderManager().getLoader(0);
        if (loader == null) {
            return;
        }
        loader.forceLoad();

        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onFooterRefresh() {
        mPage++;
        getSupportLoaderManager().restartLoader(0, null, this);

        final Loader<?> loader = getSupportLoaderManager().getLoader(0);
        if (loader == null) {
            return;
        }

        loader.forceLoad();
    }

    @Override
    public Loader<AsyncTaskLoader.LoaderResult<?>> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader mLoader = new FollowListLoader(this, mUserID, mPage, mFollowType); //TODO 这里好像有问题
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskLoader.LoaderResult<?>> loader, AsyncTaskLoader.LoaderResult<?> data) {
        if (mPage == 1) {
            mLayout.setHeaderRefreshing(false);
            if (data.hasException()) {
                Toast.makeText(mContext, "无法完成加载，请检查网络...", Toast.LENGTH_SHORT).show();
            } else if (data.mResult == null) {
                Toast.makeText(mContext, "没有更多了...", Toast.LENGTH_SHORT).show();
                mPage--;
            } else {
                Follow follow = (Follow) data.mResult;
                if (follow.getStatus() == 1) {
                    mAdapter.setDataSource(follow.getFollowList());
                } else {
                    ErrorNotification.errorNotify(mContext, follow.getError_code());
                }
            }
        } else {
            mLayout.setFooterRefreshing(false);

            Toast.makeText(mContext, "没有更多了...", Toast.LENGTH_SHORT).show();

            if (data.hasException()) {
                Toast.makeText(mContext, "无法完成加载，请检查网络...", Toast.LENGTH_SHORT).show();
            } else if (data.mResult == null) {
                Toast.makeText(mContext, "没有更多了...", Toast.LENGTH_SHORT).show();
                mPage--;
            } else {
                int pre;
                Follow follow = (Follow) data.mResult;
                if (follow.getStatus() == 1) {
                    pre = mAdapter.addDataSource(follow.getFollowList());
                    mRecyclerView.smoothScrollToPosition(pre);
                } else {
                    ErrorNotification.errorNotify(mContext, follow.getError_code());
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncTaskLoader.LoaderResult<?>> loader) {

    }

    public enum FollowType {
        Followee, Follower, Unknown
    }
}
