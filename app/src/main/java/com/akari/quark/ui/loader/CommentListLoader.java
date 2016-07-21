package com.akari.quark.ui.loader;

import android.content.Context;

import com.akari.quark.data.DataRequestHelper;
import com.akari.quark.entity.comment.Comment;

/**
 * Created by Akari on 16/7/13.
 */
public class CommentListLoader extends AsyncTaskLoader<Comment> {
    private int mPage;
    private long mID;

    public CommentListLoader(Context context, long answerID, int page) {
        super(context);
        mID = answerID;
        mPage = page;
    }

    @Override
    public Comment loadInBackgroundWithException() throws Exception {
        return DataRequestHelper.getComments(mID, mPage);
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
