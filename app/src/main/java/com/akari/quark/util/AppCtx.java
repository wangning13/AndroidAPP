package com.akari.quark.util;

import android.app.Application;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

public class AppCtx extends Application {
    private static final String TAG = AppCtx.class.getSimpleName();

    private static AppCtx mInstance;
    private EventBus mEventBus;
    private volatile boolean mIsInited;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        init();
    }

    public boolean isInited() {
        return mIsInited;
    }

    private void init() {
        // event bus is the first
        mEventBus = new AsyncEventBus(new HandlerExecutor());
        mEventBus.register(this);
    }

    public static EventBus getEventBus() {
        return mInstance.mEventBus;
    }

    public static AppCtx getInstance() {
        return mInstance;
    }

}
