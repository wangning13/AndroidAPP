package com.akari.quark.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.akari.quark.R;
import com.akari.quark.entity.Infomation;

/**
 * Created by motoon on 2016/6/1.
 */
public class SplashActivity extends Activity {
    private static final String ISCHECKED = "ischecked";
    private final int SPLASH_DISPLAY_LENGHT = 500; // 延迟
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        sharedPreferences = getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
        if (sharedPreferences.getBoolean(ISCHECKED, false)) {
            //默认是自动登录状态，直接跳转
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                    Infomation.loadInfo(SplashActivity.this);
                }

            }, SPLASH_DISPLAY_LENGHT);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGHT);
        }
    }
}
