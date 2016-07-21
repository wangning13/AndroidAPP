package com.akari.quark.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.answerDetail.AnswerDetail;
import com.akari.quark.entity.answerDetail.Message;
import com.akari.quark.entity.answerDetail.Writer;
import com.akari.quark.entity.post.Post;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.util.GsonUtil;
import com.hippo.refreshlayout.RefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by motoon on 2016/5/13.
 */
public class AnswerDetailActivity extends AppCompatActivity implements NestedScrollingParent, NestedScrollingChild, RefreshLayout.OnRefreshListener {
    public static final String X_ACCESS_TOKEN = "x-access-token";
    public static final String TEMP_X_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0ODUyNzE2MDExfQ.1sJDUeBZS0O1-Tjru2V05K8SJTPWB_D5weRuUEL1Upw";
    public static Handler sHandler = new Handler();
    private Context context;
    private RefreshLayout mRefreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_detail);
        context = AnswerDetailActivity.this;
        mRefreshlayout = (RefreshLayout) findViewById(R.id.answer_detail_container);

        final LinearLayout up_layout = (LinearLayout) this.findViewById(R.id.up_layout);
        final ImageView up = (ImageView) up_layout.findViewById(R.id.up);

        final LinearLayout down_layout = (LinearLayout) this.findViewById(R.id.down_layout);
        final ImageView down = (ImageView) down_layout.findViewById(R.id.down);

        final LinearLayout mark_layout = (LinearLayout) this.findViewById(R.id.mark_layout);
        final ImageView mark = (ImageView) mark_layout.findViewById(R.id.mark);

        final LinearLayout comment_layout = (LinearLayout) this.findViewById(R.id.comment_layout);
        final ImageView comment = (ImageView) comment_layout.findViewById(R.id.comment);
        mRefreshlayout.setHeaderColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshlayout.setFooterColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshlayout.setOnRefreshListener(this);

        Intent intent = getIntent();
        final String answerId = intent.getStringExtra("answerId");
        final String questionTitle = intent.getStringExtra("questionTitle");
        //创建OkHttpClient对象，用于稍后发起请求
        OkHttpClient client = new OkHttpClient();

        String url = OkHttpManager.API_ANSWER_DETAIL;
        String urlDetail = OkHttpManager.attachHttpGetParam(url, "answer_id", answerId);
        //根据请求URL创建一个Request对象
        Request request = new Request.
                Builder().url(urlDetail)
                .header(OkHttpManager.X_ACCESS_TOKEN, OkHttpManager.TEMP_X_ACCESS_TOKEN)
                .build();
        final Handler mHandler = new Handler(Looper.getMainLooper());
        //根据Request对象发起Get异步Http请求，并添加请求回调
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(mContext,"无法访问", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (call != null) {
                            AnswerDetail answerDetail = GsonUtil.GsonToBean(result, AnswerDetail.class);
                            Long status = answerDetail.getStatus();
                            String errorCode = answerDetail.getErrorCode();
                            if (status == 0) {
                                Toast.makeText(context, "网络链接失败请检查你的网络...", Toast.LENGTH_SHORT).show();
                            } else {
                                Message message = answerDetail.getMessage();
                                final Long id = message.getId();
                                final String content = message.getContent();
                                final Long questionId = message.getQuestionId();
                                final Long answerId = message.getAnswererId();
                                final Long createTime = message.getCreateTime();
//                                    Long deleteFlag = message.getDeleteFlag();
//                                    Long praiseNum = message.getPariseNum();
//                                    Long commentNum = message.getCommentNum();
//                                    Long downNum = message.getDownNum();
//                                    Long collectNum = message.getCollectNum();
//                                    Long readNum = message.getReadNum();
                                Writer writer = message.getWriter();
                                Boolean isCollected = message.getIsCollected();
                                final Boolean isDown = message.getIsDown();
                                final Boolean isPraised = message.getIsPraised();
                                SimpleDateFormat sdf = null;
                                Date date = null;
                                try {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    date = new Date(createTime);
                                    TextView createTimetv = (TextView) findViewById(R.id.create_time);
                                    createTimetv.setText("创建于" + sdf.format(date));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                TextView usernametv = (TextView) findViewById(R.id.username);
                                usernametv.setText(writer.getName());
                                TextView introductiontv = (TextView) findViewById(R.id.introduction);
                                introductiontv.setText(writer.getIntroduction());
                                TextView contenttv = (TextView) findViewById(R.id.content);
                                contenttv.setText(content);

                                Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
                                toolbar.setTitle("回答");
                                Toolbar toolbar2 = (Toolbar) findViewById(R.id.id_tool_bar2);
                                toolbar2.setTitle(questionTitle);
                                toolbar2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, QuestionDetailActivity.class);
                                        intent.putExtra("questionId", questionId + "");
                                        context.startActivity(intent);
                                    }
                                });
                                setSupportActionBar(toolbar);
                                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onBackPressed();
                                    }
                                });
                                if (isPraised == true) {
                                    up.setImageResource(R.drawable.up2);
                                    up.setSelected(true);
                                    down.setImageResource(R.drawable.down);
                                    down.setSelected(false);
                                } else {
                                    up.setImageResource(R.drawable.up);
                                    up.setSelected(false);
                                }
                                if (isDown == true) {
                                    down.setImageResource(R.drawable.down2);
                                    down.setSelected(true);
                                    up.setImageResource(R.drawable.up);
                                    up.setSelected(false);
                                } else {
                                    down.setImageResource(R.drawable.down);
                                    down.setSelected(false);
                                }

                                if (up != null) {

                                    up_layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (up.isSelected() == false) {
                                                up.setImageResource(R.drawable.up2);
                                                up.setSelected(true);
                                                down.setSelected(false);
                                                down.setImageResource(R.drawable.down);
                                                //向服务器post点赞
                                                String url = OkHttpManager.API_ANSWER_PRAISE;
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("answer_id", id.toString());
                                                OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                                                    @Override
                                                    public void requestFailure(Request request, IOException e) {
                                                        Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void requestSuccess(String result) throws Exception {
//                                                          Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                                                        Post post = GsonUtil.GsonToBean(result, Post.class);
                                                        Long status = post.getStatus();
                                                        String errorCode = post.getErrorCode();
                                                        if (status == 1) {

                                                        } else {
                                                            Toast.makeText(context, "点赞失败" + errorCode, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                };
                                                OkHttpManager.postAsync(url, params, callback, OkHttpManager.X_ACCESS_TOKEN, OkHttpManager.TEMP_X_ACCESS_TOKEN);
                                                //向服务器DELETE Down
                                                if (isDown == true) {
                                                    down.setImageResource(R.drawable.down);
                                                    down.setSelected(false);
                                                    String url2 = OkHttpManager.API_ANSWER_DOWN;
                                                    Map<String, String> params2 = new HashMap<String, String>();
                                                    params.put("answer_id", id.toString());
                                                    OkHttpManager.DataCallBack callback2 = new OkHttpManager.DataCallBack() {
                                                        @Override
                                                        public void requestFailure(Request request, IOException e) {
                                                            Toast.makeText(context, "取消Down失败", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void requestSuccess(String result) throws Exception {
//                                                          Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                                                            Post post = GsonUtil.GsonToBean(result, Post.class);
                                                            Long status = post.getStatus();
                                                            String errorCode = post.getErrorCode();
                                                            if (status == 1) {

                                                            } else {
                                                                Toast.makeText(context, "取消Down失败" + errorCode + "-" + id, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    };
                                                    OkHttpManager.deleteAsync(url2, params2, callback2, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
                                                }
                                            } else {
                                                //向服务器delete点赞
                                                up.setImageResource(R.drawable.up);
                                                up.setSelected(false);
                                                String url = OkHttpManager.API_ANSWER_PRAISE;
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("answer_id", id.toString());
                                                OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                                                    @Override
                                                    public void requestFailure(Request request, IOException e) {
                                                        Toast.makeText(context, "取消点赞失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void requestSuccess(String result) throws Exception {
                                                        Post post = GsonUtil.GsonToBean(result, Post.class);
                                                        Long status = post.getStatus();
                                                        String errorCode = post.getErrorCode();
                                                        if (status == 1) {

                                                        } else {
                                                            Toast.makeText(context, "取消点赞失败" + errorCode, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                };
                                                OkHttpManager.deleteAsync(url, params, callback, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);

                                            }

                                        }
                                    });
                                }

                                if (down != null) {

                                    down_layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (down.isSelected() == false) {
                                                down.setImageResource(R.drawable.down2);
                                                down.setSelected(true);
                                                up.setSelected(false);
                                                up.setImageResource(R.drawable.up);
                                                //向服务器post Down
                                                String url = OkHttpManager.API_ANSWER_DOWN;
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("answer_id", id.toString());
                                                OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                                                    @Override
                                                    public void requestFailure(Request request, IOException e) {
                                                        Toast.makeText(context, "Down失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void requestSuccess(String result) throws Exception {
                                                        Post post = GsonUtil.GsonToBean(result, Post.class);
                                                        Long status = post.getStatus();
                                                        String errorCode = post.getErrorCode();
                                                        if (status == 1) {

                                                        } else {
                                                            Toast.makeText(context, "Down失败" + errorCode, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                };
                                                OkHttpManager.postAsync(url, params, callback, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
                                                //向服务器delete点赞
                                                if (isPraised == true) {
                                                    up.setImageResource(R.drawable.up);
                                                    up.setSelected(false);
                                                    String url2 = OkHttpManager.API_ANSWER_PRAISE;
                                                    Map<String, String> params2 = new HashMap<String, String>();
                                                    params.put("answer_id", id.toString());
                                                    OkHttpManager.DataCallBack callback2 = new OkHttpManager.DataCallBack() {
                                                        @Override
                                                        public void requestFailure(Request request, IOException e) {
                                                            Toast.makeText(context, "取消点赞失败", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void requestSuccess(String result) throws Exception {
                                                            Post post = GsonUtil.GsonToBean(result, Post.class);
                                                            Long status = post.getStatus();
                                                            String errorCode = post.getErrorCode();
                                                            if (status == 1) {

                                                            } else {
                                                                Toast.makeText(context, "取消点赞失败" + errorCode, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    };
                                                    OkHttpManager.deleteAsync(url2, params2, callback2, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
                                                }
                                            } else {
                                                //向服务器DELETE Down
                                                down.setImageResource(R.drawable.down);
                                                down.setSelected(false);
                                                String url = OkHttpManager.API_ANSWER_DOWN;
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("answer_id", id.toString());
                                                OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                                                    @Override
                                                    public void requestFailure(Request request, IOException e) {
                                                        Toast.makeText(context, "取消Down失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void requestSuccess(String result) throws Exception {
                                                        Post post = GsonUtil.GsonToBean(result, Post.class);
                                                        Long status = post.getStatus();
                                                        String errorCode = post.getErrorCode();
                                                        if (status == 1) {

                                                        } else {
                                                            Toast.makeText(context, "取消Down失败" + status + errorCode, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                };
                                                OkHttpManager.deleteAsync(url, params, callback, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
                                            }

                                        }
                                    });


                                }

                                if (mark != null) {
                                    if (isCollected == true) {
                                        mark.setSelected(true);
                                        mark.setImageResource(R.drawable.mark2);
                                    } else {
                                        mark.setSelected(false);
                                        mark.setImageResource(R.drawable.mark);
                                    }

                                    mark_layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (mark.isSelected() == false) {
                                                mark.setSelected(true);
                                                mark.setImageResource(R.drawable.mark2);

                                                String url = OkHttpManager.API_ANSWER_MARK;
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("answer_id", id.toString());
                                                OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                                                    @Override
                                                    public void requestFailure(Request request, IOException e) {
                                                        Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void requestSuccess(String result) throws Exception {
//                                                          Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                                                        Post post = GsonUtil.GsonToBean(result, Post.class);
                                                        Long status = post.getStatus();
                                                        String errorCode = post.getErrorCode();
                                                        if (status == 1) {

                                                        } else {
//                                                            Toast.makeText(context,"收藏失败"+status+errorCode,Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                };
                                                OkHttpManager.postAsync(url, params, callback, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
                                            } else {
                                                mark.setSelected(false);
                                                mark.setImageResource(R.drawable.mark);

                                                String url = OkHttpManager.API_ANSWER_MARK;
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("answer_id", id.toString());
                                                OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                                                    @Override
                                                    public void requestFailure(Request request, IOException e) {
                                                        Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void requestSuccess(String result) throws Exception {
//                                                          Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                                                        Post post = GsonUtil.GsonToBean(result, Post.class);
                                                        Long status = post.getStatus();
                                                        String errorCode = post.getErrorCode();
                                                        if (status == 1) {

                                                        } else {
//                                                            Toast.makeText(context,"取消收藏失败"+status+errorCode,Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                };
                                                OkHttpManager.deleteAsync(url, params, callback, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
                                            }

                                        }
                                    });
                                }

                                if (comment != null) {
                                    comment_layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(context, CommentActivity.class);
                                            intent.putExtra("answerId", Long.parseLong(String.valueOf(answerId)));
                                            context.startActivity(intent);
                                        }
                                    });
                                }
                            }

                        }
                    }
                });

            }
        });

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

    @Override
    public boolean isNestedScrollingEnabled() {
        return false;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {

    }

    @Override
    public boolean startNestedScroll(int axes) {
        return false;
    }

    @Override
    public void stopNestedScroll() {

    }

    @Override
    public boolean hasNestedScrollingParent() {
        return false;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return false;
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

    }

    @Override
    public void onStopNestedScroll(View target) {

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    @Override
    public void onHeaderRefresh() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshlayout.setFooterRefreshing(false);
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
                mRefreshlayout.setHeaderRefreshing(false);
            }
        }, 3000);

    }
}
