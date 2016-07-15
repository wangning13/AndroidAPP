package com.akari.quark.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.akari.quark.R;
import com.akari.quark.ui.adapter.FocusFragmentPagerAdapter;
import com.akari.quark.ui.adapter.MainFragmentPagerAdapter;

/**
 * Created by Akari on 16/7/15.
 */
public class FocusFragment extends BaseFragment {

    public static FocusFragment newInstance() {
        Bundle args = new Bundle();
        FocusFragment fragment = new FocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void init(View view) {
        // init view
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        FocusFragmentPagerAdapter pagerAdapter = new FocusFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
