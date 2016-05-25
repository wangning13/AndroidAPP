package com.akari.quark.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akari.quark.R;
import com.akari.quark.entity.asksInMain.AsksInMainMessage;
import com.akari.quark.ui.adapter.AnswerRecyclerViewAdapter;
import com.akari.quark.ui.adapter.AskRecyclerViewAdapter;
import com.akari.quark.ui.adapter.baseAdapter.RecyclerViewAdapter;
import com.akari.quark.ui.listener.OnVerticalScrollListener;
import com.akari.quark.ui.loader.AsksInMainListLoader;
import com.akari.quark.ui.loader.AsyncTaskLoader;
import com.hippo.refreshlayout.RefreshLayout;

import java.util.List;

public class PageFragment extends Fragment implements RefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<AsyncTaskLoader.LoaderResult<List<AsksInMainMessage>>> {
    private static final String TAG = PageFragment.class.getSimpleName();
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private AskRecyclerViewAdapter mAdapter;
    private RefreshLayout mLayout;
    private AsksInMainListLoader mLoader;
    private RecyclerView mRecyclerView;

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayout.setHeaderRefreshing(true);
            }
        }, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        mLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        mLayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        switch (mPage){
//            case 1:
                mRecyclerView.setAdapter(mAdapter = new AskRecyclerViewAdapter(getContext()));
//                break;
//            case 2:
//                mRecyclerView.setAdapter(mAdapter = new AnswerRecyclerViewAdapter(getContext()));
//                break;
//        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledToBottom() {
                mLayout.setFooterRefreshing(true);
                onFooterRefresh();
            }
        });

        return view;
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
        final Loader<?> loader = getLoaderManager().getLoader(0);
        if (loader == null) {
            return;
        }
        loader.forceLoad();

        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onFooterRefresh() {
        final Loader<?> loader = getLoaderManager().getLoader(0);
        if (loader == null) {
            return;
        }
        loader.forceLoad();

        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public Loader<AsyncTaskLoader.LoaderResult<List<AsksInMainMessage>>> onCreateLoader(int id, Bundle args) {
        mLoader = new AsksInMainListLoader(getActivity(), 0);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskLoader.LoaderResult<List<AsksInMainMessage>>> loader, AsyncTaskLoader.LoaderResult<List<AsksInMainMessage>> data) {
        mLayout.setHeaderRefreshing(false);
        mLayout.setFooterRefreshing(false);
        if (data.hasException()) {
            return;
        }
        mAdapter.setDataSource(data.mResult);
    }

    @Override
    public void onLoaderReset(Loader<AsyncTaskLoader.LoaderResult<List<AsksInMainMessage>>> loader) {

    }
}