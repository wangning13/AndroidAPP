package com.akari.quark.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.adapter.RecyclerViewAdapter;
import com.yazhi.superswiperefreshlayout.SuperSwipeRefreshLayout;

public class PageFragment extends Fragment implements SuperSwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private SuperSwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    public static Handler sHandler = new Handler();

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeLayout = (SuperSwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //mSwipeLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh(final int type) {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case 0: //进入界面时加载的操作 refresh when activity was created
                        Toast.makeText(getContext(),"启动加载刷新",Toast.LENGTH_SHORT);
                        //关闭启动加载刷新动画
                        //finish animation
                        mSwipeLayout.setRefreshing(false);
                        break;
                    case 1: //下拉刷新 pull down to refresh
                        Toast.makeText(getContext(),"下拉刷新",Toast.LENGTH_SHORT);
                        //关闭下拉拉刷新动画
                        //finish animation
                        mSwipeLayout.setRefreshing(false);
                        break;
                    case 2: //上拉刷新 pull up to refresh
                        Toast.makeText(getContext(),"上拉刷新",Toast.LENGTH_SHORT);
                        //关闭上拉刷新动画
                        //finish animation
                        mSwipeLayout.setUpRefreshing(false);
                        break;
                    default:
                        break;
                }
            }
        }, 3000);
    }
}