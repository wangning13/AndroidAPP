package com.akari.quark.ui.loader;

import android.content.Context;

import com.akari.quark.data.DataRequestHelper;
import com.akari.quark.entity.answersInMain.AnswersInMainMessage;

import java.util.List;

public class AnswersInMainListLoader extends AsyncTaskLoader<List<AnswersInMainMessage>> {
    private int mPage;

    public AnswersInMainListLoader(Context context, int page) {
        super(context);

        mPage = page;
    }

    @Override
    public List<AnswersInMainMessage> loadInBackgroundWithException() throws Exception {
        final List<AnswersInMainMessage> answers = DataRequestHelper.getAnswersInMain(mPage);
        return answers;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }
}
