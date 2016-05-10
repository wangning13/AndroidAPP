package com.akari.quark.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.akari.quark.R;

/**
 * Created by motoon on 2016/5/6.
 */
public class QuestionDetailActivity extends AppCompatActivity {
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        context = QuestionDetailActivity.this;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.toolbar_menu);
//
//        toolbar.setNavigationIcon(R.mipmap.menu);
//
//        toolbar.setTitle("6月5日创建");
//        toolbar.setTitleTextColor(Color.parseColor("#1E73B3"));



    }
}
