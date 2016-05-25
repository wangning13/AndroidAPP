package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akari.quark.R;

/**
 * Created by motoon on 2016/5/13.
 */
public class AnswerDetailActivity extends AppCompatActivity{
    private Context context;
    private  boolean isdown = true;

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
        TextView content = (TextView) findViewById(R.id.content);

        final ImageView up = (ImageView) findViewById(R.id.up);
        LinearLayout up_layout = (LinearLayout)this.findViewById(R.id.up_layout);
        if (up != null) {
            up_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    up.setImageResource(R.drawable.up2);
                }
            });
        }
        final ImageView down = (ImageView) findViewById(R.id.down);
        LinearLayout down_layout = (LinearLayout)this.findViewById(R.id.down_layout);
        if (down != null) {
            if(isdown==true){
                down_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        down.setImageResource(R.drawable.down2);
                        isdown = false;
                    }
                });
            }else{
                down_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        down.setImageResource(R.drawable.down);
                        isdown = true;
                    }
                });
            }

        }
        final ImageView mark = (ImageView) findViewById(R.id.mark);
        LinearLayout mark_layout = (LinearLayout)this.findViewById(R.id.mark_layout);
        if (mark != null) {
            mark_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mark.setImageResource(R.drawable.mark2);
                }
            });
        }
        final ImageView comment = (ImageView) findViewById(R.id.comment);
        LinearLayout comment_layout = (LinearLayout)this.findViewById(R.id.comment_layout);
        if (comment != null) {
            comment_layout.setOnClickListener(new View.OnClickListener() {
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
