package com.akari.quark.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.akari.quark.R;

/**
 * Created by motoon on 2016/5/13.
 */
public class AnswerDetailActivity extends AppCompatActivity{
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_detail);
        context = AnswerDetailActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final ImageButton up = (ImageButton) findViewById(R.id.up);
        if (up != null) {
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    up.setImageResource(R.drawable.up2);
                }
            });
        }
        final ImageButton down = (ImageButton) findViewById(R.id.down);
        if (down != null) {
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    down.setImageResource(R.drawable.down2);
                }
            });
        }
        final ImageButton mark = (ImageButton) findViewById(R.id.mark);
        if (mark != null) {
            mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mark.setImageResource(R.drawable.mark2);
                }
            });
        }
        final ImageButton comment = (ImageButton) findViewById(R.id.comment);
        if (comment != null) {
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
