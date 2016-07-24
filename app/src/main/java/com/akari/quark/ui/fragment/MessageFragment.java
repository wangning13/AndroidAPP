package com.akari.quark.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.akari.quark.R;
import com.akari.quark.ui.adapter.MessageFragmentPagerAdapter;

/**
 * Created by motoon on 2016/7/24.
 */
public class MessageFragment  extends BaseFragment {
    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.message_main;
    }

    @Override
    protected void init(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.message_viewPager);
        MessageFragmentPagerAdapter pagerAdapter = new MessageFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.message_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
