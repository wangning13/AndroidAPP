package com.akari.quark.ui.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.akari.quark.R;
import com.akari.quark.entity.comment.CommentMessage;
import com.akari.quark.ui.adapter.CommentRecyclerViewAdapter;
import com.akari.quark.ui.adapter.baseAdapter.NewRecyclerViewAdapter;
import com.akari.quark.ui.listener.OnVerticalScrollListener;
import com.akari.quark.ui.loader.AsyncTaskLoader;
import com.akari.quark.ui.loader.CommentListLoader;
import com.hippo.refreshlayout.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends Activity implements RefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<AsyncTaskLoader.LoaderResult<List<?>>> {
    private static final String TAG = CommentActivity.class.getSimpleName();

    private int mPage;
    private long mAnswerID;

    private Context mContext;
    private NewRecyclerViewAdapter mAdapter;
    private RefreshLayout mLayout;
    private AsyncTaskLoader mLoader;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mContext = CommentActivity.this;
        mPage = 1;
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

        final LoaderManager loaderManager = getLoaderManager();
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
        List<CommentMessage> list = new ArrayList<CommentMessage>();
        CommentMessage message = new CommentMessage();
        message.setContent("haha");
        list.add(message);
        mLayout.setHeaderRefreshing(false);
        mAdapter.setDataSource(list);
    }

    @Override
    public void onFooterRefresh() {
        mPage++;
        List<CommentMessage> list = new ArrayList<CommentMessage>();
        CommentMessage message = new CommentMessage();
        message.setContent("haha");
        list.add(message);
        mLayout.setFooterRefreshing(false);
        mAdapter.addDataSource(list);
    }

    @Override
    public android.content.Loader<AsyncTaskLoader.LoaderResult<List<?>>> onCreateLoader(int id, Bundle args) {
        mLoader = new CommentListLoader(this, mPage, 1);
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<AsyncTaskLoader.LoaderResult<List<?>>> loader, AsyncTaskLoader.LoaderResult<List<?>> data) {

    }

    @Override
    public void onLoaderReset(android.content.Loader<AsyncTaskLoader.LoaderResult<List<?>>> loader) {

    }
}
