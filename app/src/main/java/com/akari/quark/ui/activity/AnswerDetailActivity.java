package com.akari.quark.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.akari.quark.entity.answerDetail.Answer;
import com.akari.quark.entity.answerDetail.AnswerDetail;
import com.akari.quark.entity.answerDetail.User;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.util.GsonUtil;

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
public class AnswerDetailActivity extends AppCompatActivity{
    private Context context;
    public static final String X_ACCESS_TOKEN="x-access-token";
    public static final String TEMP_X_ACCESS_TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0ODUyNzE2MDExfQ.1sJDUeBZS0O1-Tjru2V05K8SJTPWB_D5weRuUEL1Upw";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_detail);

        Intent intent = getIntent();
        final String answerId = intent.getStringExtra("answerId");
        //创建OkHttpClient对象，用于稍后发起请求
        OkHttpClient client = new OkHttpClient();

        String url = OkHttpManager.API_ANSWER_DETAIL;
        String urlDetail = OkHttpManager.attachHttpGetParam(url,"answer_id",answerId);
        //根据请求URL创建一个Request对象
        Request request = new Request.
                Builder().url(urlDetail)
                .header(OkHttpManager.X_ACCESS_TOKEN,OkHttpManager.TEMP_X_ACCESS_TOKEN)
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
                        if(call!=null){
                            AnswerDetail answerDetail = GsonUtil.GsonToBean(result,AnswerDetail.class);
                            Long status = answerDetail.getStatus();
                            if(status!=0){
                                String message = answerDetail.getMessage();
                                User user = answerDetail.getUser();
                                Long UserId = user.getId();
                                String username = user.getName();
                                String userImgUrl = user.getImgUrl();
                                String introduction = user.getIntroduction();
                                Answer answer = answerDetail.getAnswer();
                                Long Id = answer.getId();
                                String content = answer.getContent();
                                Long questionId = answer.getQuestionId();
                                Long answererId = answer.getAnswererId();
                                Long create_time = answer.getCreateTime();
                                Long delete_flag = answer.getDeleteFlag();
                                Long praiseNum = answer.getPariseNum();
                                Long commentNum = answer.getCommentNum();
                                Long downNum = answer.getDownNum();
                                Long collectNum = answer.getCollectNum();

                                SimpleDateFormat sdf= null;
                                Date date = null;
                                try {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    date = new Date(create_time);
                                    System.out.println(sdf.format(date));
                                    TextView createTimetv = (TextView) findViewById(R.id.create_time);
                                    createTimetv.setText("创建于"+sdf.format(date));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                TextView usernametv = (TextView) findViewById(R.id.username);
                                usernametv.setText(username);
                                TextView introductiontv = (TextView) findViewById(R.id.introduction);
                                introductiontv.setText(introduction);
                                TextView contenttv = (TextView) findViewById(R.id.content);
                                contenttv.setText(content);


                            }

                        }
                    }
                });

            }
        });

//        TextView contenttv = (TextView) findViewById(R.id.content);
//        TextView usernametv = (TextView)findViewById(R.id.username);
//        TextView introductiontv = (TextView) findViewById(R.id.introduction);

//        contenttv.setText(content);
//        usernametv.setText(username);
//        introductiontv.setText(introduction);

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


        final ImageView up = (ImageView) findViewById(R.id.up);
        LinearLayout up_layout = (LinearLayout)this.findViewById(R.id.up_layout);
        final ImageView down = (ImageView) findViewById(R.id.down);
        LinearLayout down_layout = (LinearLayout)this.findViewById(R.id.down_layout);
        if (up != null) {
            up_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(up.isSelected()==false){
                        up.setImageResource(R.drawable.up2);
                        up.setSelected(true);
                        down.setSelected(false);
                        down.setImageResource(R.drawable.down);
                        String url = OkHttpManager.API_ANSWER_PRAISE;
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("answer_id",answerId);
                        OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {
                                Toast.makeText(context,"点赞失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void requestSuccess(String result) throws Exception {
//                                Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                            }
                        };
                        OkHttpManager.postAsync(url,params,callback,X_ACCESS_TOKEN,TEMP_X_ACCESS_TOKEN);
                    }else{
                        up.setImageResource(R.drawable.up);
                        up.setSelected(false);
                        String url = OkHttpManager.API_ANSWER_PRAISE;
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("answer_id",answerId);
                        OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {
                                Toast.makeText(context,"取消点赞失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void requestSuccess(String result) throws Exception {
//                                Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                            }
                        };
                        OkHttpManager.deleteAsync(url,params,callback,X_ACCESS_TOKEN,TEMP_X_ACCESS_TOKEN);

                    }

                }
            });
        }

        if (down != null) {
            down_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(down.isSelected()==false){
                        down.setImageResource(R.drawable.down2);
                        down.setSelected(true);
                        up.setSelected(false);
                        up.setImageResource(R.drawable.up);
                    }else{
                        down.setImageResource(R.drawable.down);
                        down.setSelected(false);
                    }

                }
            });


        }
        final ImageView mark = (ImageView) findViewById(R.id.mark);
        LinearLayout mark_layout = (LinearLayout)this.findViewById(R.id.mark_layout);
        if (mark != null) {
            mark_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mark.isSelected()==false){
                        mark.setSelected(true);
                        mark.setImageResource(R.drawable.mark2);
                    }else{
                        mark.setSelected(false);
                        mark.setImageResource(R.drawable.mark);
                    }

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
