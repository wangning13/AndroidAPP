package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.comment.Comment;
import com.akari.quark.ui.adapter.CommentRecyclerViewAdapter;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;
import com.akari.quark.ui.listener.OnVerticalScrollListener;
import com.akari.quark.ui.loader.AsyncTaskLoader;
import com.akari.quark.ui.loader.CommentListLoader;
import com.akari.quark.ui.tool.ErrorNotification;
import com.hippo.refreshlayout.RefreshLayout;

public class CommentActivity extends FragmentActivity implements RefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<AsyncTaskLoader.LoaderResult<?>> {
    private static final String TAG = CommentActivity.class.getSimpleName();

    private int mPage;
    private long mAnswerID;

    private Context mContext;
    private NewRecyclerViewAdapter<CommentRecyclerViewAdapter.NormalViewHolder> mAdapter;
    private RefreshLayout mLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mContext = CommentActivity.this;
        mPage = 1;
        mAnswerID = getIntent().getLongExtra("answerID", 0);
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

        final LoaderManager loaderManager = getSupportLoaderManager();
        if (loaderManager.getLoader(0) != null) {
            // already loaded
            return;
        }
        loaderManager.initLoader(0, null, this);
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
        AsyncTaskLoader mLoader = new CommentListLoader(this, mAnswerID, mPage);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskLoader.LoaderResult<?>> loader, AsyncTaskLoader.LoaderResult<?> data) {
        if(mPage == 1) {
            mLayout.setHeaderRefreshing(false);
            if (data.hasException()) {
                Toast.makeText(mContext, "无法完成加载，请检查网络...", Toast.LENGTH_SHORT).show();
            } else if (data.mResult == null){
                Toast.makeText(mContext, "没有更多了...", Toast.LENGTH_SHORT).show();
                mPage--;
            }else{
                Comment comment = (Comment) data.mResult;
                if (comment.getStatus() == 1) {
                    mAdapter.setDataSource(comment.getMessageList());
                } else {
                    ErrorNotification.errorNotify(mContext, comment.getError_code());
                }
            }
        } else {
            mLayout.setFooterRefreshing(false);
            if (data.hasException()) {
                Toast.makeText(mContext, "无法完成加载，请检查网络...", Toast.LENGTH_SHORT).show();
            } else if (data.mResult == null){
                Toast.makeText(mContext, "没有更多了...", Toast.LENGTH_SHORT).show();
                mPage--;
            } else {
                int pre;
                Comment comment = (Comment) data.mResult;
                if (comment.getStatus() == 1) {
                    pre = mAdapter.addDataSource(comment.getMessageList());
                    mRecyclerView.smoothScrollToPosition(pre);
                } else {
                    ErrorNotification.errorNotify(mContext, comment.getError_code());
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncTaskLoader.LoaderResult<?>> loader) {

    }
}
