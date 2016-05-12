package com.akari.quark.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.akari.quark.R;
import com.akari.quark.adapter.AnswerItemDecoration;
import com.akari.quark.adapter.QuestionDetailRecycleViewAdapter;
import com.akari.quark.data.DataDemo;

/**
 * Created by motoon on 2016/5/6.
 */
public class QuestionDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Context context;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshlayout;
    private LinearLayoutManager mLinearLayoutManager;
    private QuestionDetailRecycleViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        context = QuestionDetailActivity.this;
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
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                    Intent intent = new Intent(context, CreatQuestionActivity.class);
//                    startActivity(intent);
                }
            });
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.answer_list);
        mRefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
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
                Toast.makeText(QuestionDetailActivity.this, "data:" + data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh()
    {
        new UpdateTask().execute();
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
            mRefreshlayout.setRefreshing(false);
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
