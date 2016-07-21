package com.akari.quark.ui.loader;

import android.content.Context;

import com.akari.quark.data.DataRequestHelper;
import com.akari.quark.entity.follow.Follow;
import com.akari.quark.ui.activity.FollowActivity.FollowType;

/**
 * Created by Akari on 16/7/21.
 */
public class FollowListLoader extends AsyncTaskLoader<Follow> {
    private int mPage;
    private long mID;
    private FollowType mFollowType;

    public FollowListLoader(Context context, long userID, int page, FollowType followType) {
        super(context);
        mID = userID;
        mPage = page;
        mFollowType = followType;
    }

    @Override
    public Follow loadInBackgroundWithException() throws Exception {
        switch (mFollowType) {
            case Followee:
                return DataRequestHelper.getFollowees(mID, mPage);
            case Follower:
                return DataRequestHelper.getFollowers(mID, mPage);
            default:
                return null;
        }
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public long getmID() {
        return mID;
    }

    public void setmID(long mID) {
        this.mID = mID;
    }
}
