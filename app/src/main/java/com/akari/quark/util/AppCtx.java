package com.akari.quark.util;

import android.app.Application;

import java.util.List;

/**
 * Created by Akari on 16/5/21.
 */
public class AppCtx extends Application {
    private static final String TAG = AppCtx.class.getSimpleName();

    private static AppCtx mInstance;
    private volatile boolean mIsInited;

    private static class AppCtxHolder {
        private static final AppCtx mInstance = new AppCtx();
    }

    private AppCtx() {
    }

    public static final AppCtx getInstance() {
        return AppCtxHolder.mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public boolean isInited() {
        return mIsInited;
    }

}
