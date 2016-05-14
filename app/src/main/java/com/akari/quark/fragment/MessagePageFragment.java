package com.akari.quark.fragment;

/**
 * Created by motoon on 2016/5/14.
 */
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akari.quark.R;
import com.akari.quark.adapter.MessageRecyclerViewAdapter;
import com.akari.quark.adapter.baseAdapter.RecyclerViewAdapter;
import com.akari.quark.listener.OnVerticalScrollListener;
import com.hippo.refreshlayout.RefreshLayout;

public class MessagePageFragment extends Fragment implements RefreshLayout.OnRefreshListener {
    public static final String ARG_PAGE_MSG = "ARG_PAGE";
    public static Handler sHandler = new Handler();
    private int mPage;
    private RefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private MessageRecyclerViewAdapter mRecyclerViewAdapter;

    public static MessagePageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_MSG, page);
        MessagePageFragment fragment = new MessagePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE_MSG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_content_main, container, false);

        mSwipeLayout = (RefreshLayout) view.findViewById(R.id.message_swipe_container);
        mSwipeLayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        switch (mPage){
            case 1:
                mRecyclerView.setAdapter(mRecyclerViewAdapter = new MessageRecyclerViewAdapter(getContext()));
                break;
            case 2:

            case 3:

        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledToBottom() {
                mSwipeLayout.setFooterRefreshing(true);
                onFooterRefresh();
            }
        });

        return view;
    }

    @Override
    public void onHeaderRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerViewAdapter.refresh();
                mRecyclerView.scrollToPosition(0);
                mSwipeLayout.setHeaderRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onFooterRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(mRecyclerViewAdapter.loadMore());
                mSwipeLayout.setFooterRefreshing(false);
            }
        }, 3000);
    }
}