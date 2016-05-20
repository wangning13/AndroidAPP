package com.akari.quark.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akari.quark.R;

/**
 * Created by motoon on 2016/5/6.
 */
public class CreatQuestionActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_question);
        context = CreatQuestionActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
        toolbar.setTitle("一句话概括你的问题");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.editor_menu);
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

        EditText newTitleText = (EditText) findViewById(R.id.newTitleText);
        newTitleText.setMovementMethod(ScrollingMovementMethod.getInstance());
        newTitleText.setSelection(newTitleText.getText().length(),newTitleText.getText().length());

        EditText newTitleTopic = (EditText) findViewById(R.id.newTitleTopic);
        newTitleTopic.setMovementMethod(ScrollingMovementMethod.getInstance());
        newTitleTopic.setSelection(newTitleTopic.getText().length(),newTitleTopic.getText().length());

        EditText newQuestionDescription = (EditText) findViewById(R.id.newQuestionDescription);
        newQuestionDescription.setMovementMethod(ScrollingMovementMethod.getInstance());
        newQuestionDescription.setSelection(newQuestionDescription.getText().length(),newQuestionDescription.getText().length());
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(getApplicationContext(),"发表成功！",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
