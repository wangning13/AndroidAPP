package com.akari.quark.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.akari.quark.R;

/**
 * Created by motoon on 2016/5/6.
 */
public class QuestionDetailActivity extends Activity {
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        context = QuestionDetailActivity.this;



    }
}
