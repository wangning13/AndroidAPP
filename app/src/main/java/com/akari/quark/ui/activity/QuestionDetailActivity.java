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
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.questionDetail.Message;
import com.akari.quark.entity.questionDetail.QuestionDetail;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.ui.adapter.QuestionDetailRecycleViewAdapter;
import com.akari.quark.util.GsonUtil;
import com.hippo.refreshlayout.RefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

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
    private int mPage;
    String question_id;
    View header;
    public static Handler sHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        context = QuestionDetailActivity.this;
        mActivity = this;
        mPage = 1;


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
                    bundle.putString("questionId",getIntent().getStringExtra("questionId"));
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

        //每个item高度一致，可设置为true，提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        header = LayoutInflater.from(context).inflate(R.layout.question_headerview, mRecyclerView, false);
        final Button button = (Button) header.findViewById(R.id.concern_button);
        TextView item_tag = (TextView) header.findViewById(R.id.topic);
        item_tag.setMovementMethod(ScrollingMovementMethod.getInstance());
        item_tag.setHorizontallyScrolling(true);

        Intent intent = getIntent();
        question_id = intent.getStringExtra("questionId");

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
                    //向服务器post关注
                    String url = OkHttpManager.API_QUESTION_FOCUS;
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("question_id",question_id);
                    OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, IOException e) {
                            Toast.makeText(context,"关注失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
//                                Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                        }
                    };
                    OkHttpManager.postAsync(url,params,callback,OkHttpManager.X_ACCESS_TOKEN,OkHttpManager.TEMP_X_ACCESS_TOKEN);
                }else {
                    button.setText("关注");
                    button.setBackgroundColor(Color.parseColor("#00A162"));
                    button.setSelected(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        button.setBackground(context.getResources().getDrawable(R.drawable.shape));
                    }

                    //向服务器delete关注
                    String url = OkHttpManager.API_QUESTION_FOCUS;
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("question_id",question_id);
                    OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, IOException e) {
                            Toast.makeText(context,"取消关注失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
//                            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                        }
                    };
                    OkHttpManager.deleteAsync(url,params,callback,OkHttpManager.X_ACCESS_TOKEN,OkHttpManager.TEMP_X_ACCESS_TOKEN);
                }
            }
        });


        String url = OkHttpManager.API_QUESTION_DETAIL;
        String url1 = OkHttpManager.attachHttpGetParam(url,"question_id",question_id);
        String urlDetail = url1+"&answer_page="+mPage;
        String key = OkHttpManager.X_ACCESS_TOKEN;
        String token = OkHttpManager.TEMP_X_ACCESS_TOKEN;
        OkHttpManager.DataCallBack datacallback = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e){
                Toast.makeText(context,"无法访问", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                QuestionDetail questinoDetail = GsonUtil.GsonToBean(result,QuestionDetail.class);
                Message message = questinoDetail.getMessage();
                mAdapter = new QuestionDetailRecycleViewAdapter(context,message);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setHeaderView(header);

                Long create_time = message.getCreateTime();
                SimpleDateFormat sdf= null;
                Date date = null;
                try {
                    sdf = new SimpleDateFormat("MM月dd日创建");
                    date = new Date(create_time);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
                    toolbar.setTitle(sdf.format(date));
                    setSupportActionBar(toolbar);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        OkHttpManager.getAsync(urlDetail,datacallback,key,token);

    }

    @Override
    public void onHeaderRefresh() {
        int times = 3000;
        refreshData(times);
    }

    private void refreshData(int times) {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPage = 1;
                mRecyclerView.scrollToPosition(0);
                mRefreshlayout.setHeaderRefreshing(false);

                String url = OkHttpManager.API_QUESTION_DETAIL;
                String url1 = OkHttpManager.attachHttpGetParam(url,"question_id",question_id);
                String urlDetail = url1+"&answer_page="+mPage;
                String key = OkHttpManager.X_ACCESS_TOKEN;
                String token = OkHttpManager.TEMP_X_ACCESS_TOKEN;
                OkHttpManager.DataCallBack datacallback = new OkHttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e){
                        Toast.makeText(context,"无法访问", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        QuestionDetail questinoDetail = GsonUtil.GsonToBean(result,QuestionDetail.class);
                        Message message = questinoDetail.getMessage();
                        mAdapter.notifyDataSetChanged();
                        mAdapter = new QuestionDetailRecycleViewAdapter(context,message);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setHeaderView(header);

                        Long create_time = message.getCreateTime();
                        SimpleDateFormat sdf= null;
                        Date date = null;
                        try {
                            sdf = new SimpleDateFormat("MM月dd日创建");
                            date = new Date(create_time);
                            Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
                            toolbar.setTitle(sdf.format(date));
                            setSupportActionBar(toolbar);
                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onBackPressed();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                OkHttpManager.getAsync(urlDetail,datacallback,key,token);
            }
        }, times);
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
