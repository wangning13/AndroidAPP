package com.akari.quark.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akari.quark.R;
import com.akari.quark.data.DataDemo;
import com.akari.quark.ui.adapter.QuestionDetailRecycleViewAdapter;
import com.hippo.refreshlayout.RefreshLayout;

/**
 * Created by motoon on 2016/5/6.
 */
public class QuestionDetailActivity extends AppCompatActivity implements RefreshLayout.OnRefreshListener{
    private Context context;
    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshlayout;
    private LinearLayoutManager mLinearLayoutManager;
    private QuestionDetailRecycleViewAdapter mAdapter;
    private Activity mActivity;
    public static Handler sHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        context = QuestionDetailActivity.this;
        mActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_question_detail);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionDetailActivity.this, EditorActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(EditorActivity.CONTENT_PARAM, "");
                    bundle.putString(EditorActivity.CONTENT_PLACEHOLDER_PARAM,
                            getString(R.string.example_post_content_placeholder));
                    bundle.putInt(EditorActivity.EDITOR_PARAM, EditorActivity.USE_NEW_EDITOR);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.answer_list);
        mRefreshlayout = (RefreshLayout) findViewById(R.id.swipe_container);
        mRefreshlayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshlayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshlayout.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new QuestionDetailRecycleViewAdapter(DataDemo.generateData(20));
        mRecyclerView.setAdapter(mAdapter);
        //每个item高度一致，可设置为true，提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        View header = LayoutInflater.from(context).inflate(R.layout.question_headerview, mRecyclerView, false);
        final Button button = (Button) header.findViewById(R.id.concern_button);
        TextView item_tag = (TextView) header.findViewById(R.id.item_tag);
        item_tag.setMovementMethod(ScrollingMovementMethod.getInstance());
        item_tag.setHorizontallyScrolling(true);

        final boolean state = button.isSelected();
        //为关注按钮设置响应事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.isSelected()==false){
                    button.setText("已关注");
                    button.setBackgroundColor(Color.parseColor("#D1D1D1"));
                    button.setSelected(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        button.setBackground(context.getResources().getDrawable(R.drawable.shape));
                    }
                }else {
                    button.setText("关注");
                    button.setBackgroundColor(Color.parseColor("#00A162"));
                    button.setSelected(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        button.setBackground(context.getResources().getDrawable(R.drawable.shape));
                    }
                }
            }
        });

        mAdapter.setHeaderView(header);


        //为每个item增加响应事件
        mAdapter.setOnItemClickListener(new QuestionDetailRecycleViewAdapter.OnItemClickListener()
        {
            @Override
            public void OnItemClick(View view, String data)
            {
                Intent intent = new Intent(context,AnswerDetailActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public void onHeaderRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(0);
                mRefreshlayout.setHeaderRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onFooterRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshlayout.setFooterRefreshing(false);
            }
        }, 3000);
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