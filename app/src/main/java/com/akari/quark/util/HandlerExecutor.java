package com.akari.quark.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class HandlerExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable command) {
        ExecutorUtils.runInUiThread(command);
    }
}
