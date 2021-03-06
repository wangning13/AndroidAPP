package com.akari.quark.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;

import com.akari.quark.util.AppCtx;


public class DeviceStatus {
    private static final DeviceStatus instance;

    static {
        instance = new DeviceStatus(AppCtx.getInstance());
    }

    private final ConnectivityManager mConnectivityManager;
    private boolean mIsNetworkMetered;
    private boolean mIsNetworkConnected;

    DeviceStatus(Context context) {
        mConnectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateNetworkStatus();
            }
        }, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        updateNetworkStatus();
    }

    public static DeviceStatus getInstance() {
        return instance;
    }

    private void updateNetworkStatus() {
        mIsNetworkMetered = ConnectivityManagerCompat.isActiveNetworkMetered(mConnectivityManager);

        final NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            mIsNetworkConnected = false;
        } else {
            mIsNetworkConnected = activeNetworkInfo.isConnected();
        }
    }

    public boolean isNetworkMetered() {
        return mIsNetworkMetered;
    }

    public boolean isNetworkConnected() {
        return mIsNetworkConnected;
    }
}
