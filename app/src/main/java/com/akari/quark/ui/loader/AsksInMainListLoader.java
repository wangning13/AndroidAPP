package com.akari.quark.ui.loader;

import android.content.Context;

import com.akari.quark.data.DataRequestHelper;
import com.akari.quark.entity.asksInMain.AsksInMainMessage;

import java.util.List;

public class AsksInMainListLoader extends AsyncTaskLoader<List<AsksInMainMessage>> {
    private int mPage;

    public AsksInMainListLoader(Context context, int page) {
        super(context);

        mPage = page;
    }

    @Override
    public List<AsksInMainMessage> loadInBackgroundWithException() throws Exception {
        final List<AsksInMainMessage> asks = DataRequestHelper.getAsksInMain(mPage);
        return asks;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }
}
