package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.data.DataPostHelper;
import com.akari.quark.network.OkHttpManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by motoon on 2016/5/6.
 */
public class CreatQuestionActivity extends AppCompatActivity {

    private Context context;
    private EditText newTitleText;
    private EditText newTitleTopic;
    private EditText newQuestionDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_question);
        context = CreatQuestionActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
        toolbar.setTitle("一句话概括你的问题");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        EditScroll();
    }

    //设置滚动
    private void EditScroll() {

        newTitleText = (EditText) findViewById(R.id.newTitleText);
        newTitleText.setMovementMethod(ScrollingMovementMethod.getInstance());
        newTitleText.setSelection(newTitleText.getText().length(), newTitleText.getText().length());

        newTitleTopic = (EditText) findViewById(R.id.newTitleTopic);
        newTitleTopic.setMovementMethod(ScrollingMovementMethod.getInstance());
        newTitleTopic.setSelection(newTitleTopic.getText().length(), newTitleTopic.getText().length());

        newQuestionDescription = (EditText) findViewById(R.id.newQuestionDescription);
        newQuestionDescription.setMovementMethod(ScrollingMovementMethod.getInstance());
        newQuestionDescription.setSelection(newQuestionDescription.getText().length(), newQuestionDescription.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("ID", id + "");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            postQuestion();
        }

        return super.onOptionsItemSelected(item);
    }

    private void postQuestion() {
        if (TextUtils.isEmpty(newTitleText.getText())) {
            newTitleText.setError("此处不能为空");
            return;
        }
        if (TextUtils.isEmpty(newTitleTopic.getText())) {
            newTitleTopic.setError("此处不能为空");
            return;
        }
        if (TextUtils.isEmpty(newQuestionDescription.getText())) {
            newQuestionDescription.setError("此处不能为空");
            return;
        }

        final String title = newTitleText.getText().toString();
        final String content = newQuestionDescription.getText().toString();

        String url = DataPostHelper.API_ADD_QUESTION;
        Map<String, String> params = new HashMap<String, String>();
        params.put("question_title", title);
        params.put("description", content);
        params.put("tags", "1-6");

        OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(context, "问题创建失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                finish();
                Toast.makeText(context, "问题创建成功", Toast.LENGTH_SHORT).show();
            }
        };

        OkHttpManager.postAsync(url, params, callback, DataPostHelper.X_ACCESS_TOKEN, DataPostHelper.TEMP_X_ACCESS_TOKEN);
    }


}
