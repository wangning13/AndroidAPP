package com.akari.quark.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.akari.quark.R;
import com.akari.quark.adapter.AnswerItemDecoration;
import com.akari.quark.adapter.QuestionDetailRecycleViewAdapter;
import com.akari.quark.data.DataDemo;
import com.hippo.refreshlayout.RefreshLayout;

import org.wordpress.android.editor.Utils;

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
        //为关注按钮设置响应事件
        final Button button = (Button) findViewById(R.id.concern_button);
//        if (button != null) {
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (button.getText().equals("关注")){
//                        System.out.println("有木有关注");
//                        button.setText("已关注");
//                        button.setBackgroundColor(Color.parseColor("#D1D1D1"));
//                    }
//                    if (button.getText().equals("已关注")){
//                        button.setText("关注");
//                        System.out.println("到死有木有关注");
//                        button.setBackgroundColor(Color.parseColor("#00A162"));
//                    }
//                }
//            });
//        }
        if(button.getText().equals("关注")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setText("已关注");
                    button.setBackgroundColor(Color.parseColor("#D1D1D1"));
                    button.setId(R.id.concern_button);
                }
            });

        }
        if(button.getText().equals("已关注")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setText("关注");
                    button.setBackgroundColor(Color.parseColor("#00A162"));
                    button.setId(R.id.concern_button);
//                    button.setBackground(Drawable.createFromPath("@drawable/shape"));
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
        //分隔线
        mRecyclerView.addItemDecoration(new AnswerItemDecoration(this));
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
        new UpdateTask().execute();
    }

    @Override
    public void onFooterRefresh() {
        mRefreshlayout.setFooterRefreshing(false);
    }

    private class UpdateTask extends AsyncTask<Void,Void,List<String>>
    {
        @Override
        protected List<String> doInBackground(Void... params)
        {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            List<String> strings = new ArrayList<>();
            strings.add("新数据1");
            strings.add("新数据2");
            strings.add("新数据3");
            strings.add("新数据4");
            return strings;
        }
        @Override
        protected void onPostExecute(List<String> strings)
        {
            mAdapter.addItems(strings);
            //通知刷新完毕
            mRefreshlayout.setHeaderRefreshing(false);
            //滚动到列首部--->这是一个很方便的api，可以滑动到指定位置
            mRecyclerView.scrollToPosition(0);
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
