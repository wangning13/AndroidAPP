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

public class CommentActivity extends AppCompatActivity implements RefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<AsyncTaskLoader.LoaderResult<?>>, CommentHelper.OnCommentListener {
    private static final String TAG = CommentActivity.class.getSimpleName();

    private int mPage;
    private long mAnswerID;

    private Context mContext;
    private NewRecyclerViewAdapter<CommentRecyclerViewAdapter.NormalViewHolder> mAdapter;
    private Toolbar mToolbar;
    private RefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private CommentHelper mCommentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mContext = CommentActivity.this;
        mPage = 1;
        mAnswerID = getIntent().getLongExtra("answerID", 0);

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

        mCommentHelper = new CommentHelper((View) findViewById(R.id.reply_form), this);

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
        AsyncTaskLoader mLoader = new CommentListLoader(this, 1, mPage); //TODO 这里好像有问题
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

    @Override
    public void onReply(CharSequence content) {
        String url = OkHttpManager.API_ADD_COMMENT;
        Map<String, String> body = new HashMap<String, String>();
        body.put("answer_id", String.valueOf(1)); //TODO 这里好像有问题
        body.put("comment_content", String.valueOf(content));
        body.put("replyee_id", "1"); //TODO 暂时都设为1，以后统一改

        OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getApplicationContext(), "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                CommentResult commentResult = GsonUtil.GsonToBean(result,CommentResult.class);
                int status = commentResult.getStatus();
                int errorCode = commentResult.getError_code();
                if(status==1){
                    mCommentHelper.setContent("");

                    mLayout.setHeaderRefreshing(true);
                    onHeaderRefresh();
                }else{
                    ErrorNotification.errorNotify(mContext, errorCode);
                }
            }
        };
        OkHttpManager.postAsync(url, body, callback, OkHttpManager.X_ACCESS_TOKEN, OkHttpManager.TEMP_X_ACCESS_TOKEN);
    }
}
