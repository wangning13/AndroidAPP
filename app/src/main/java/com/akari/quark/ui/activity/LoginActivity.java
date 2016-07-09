package com.akari.quark.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.login.Login;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.util.GsonUtil;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by motoon on 2016/6/3.
 */
public class LoginActivity extends Activity {
    private static final String ISCHECKED = "ischecked";
    private static final String EMAIL = "email";
    private static final String PWD = "pwd";
    private static final String TOKEN = "token";
    private Context context;
    private SharedPreferences sharedPreferences;
    String token;
    EditText emailEdit;
    EditText pwdEdit;
    Button loginButton;
    TextView forgetPwd;
    TextView userRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = LoginActivity.this;

        emailEdit = (EditText) findViewById(R.id.email_edit);
        pwdEdit = (EditText) findViewById(R.id.password_edit);
        //可以被其他应用程序读
        sharedPreferences = getSharedPreferences(ISCHECKED, Context.MODE_WORLD_READABLE);
        loginButton = (Button) findViewById(R.id.login_button);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        userRegister = (TextView) findViewById(R.id.user_register);
        if (sharedPreferences.getBoolean("AUTO_ISCHECK",false)){
            emailEdit.setText(sharedPreferences.getString(EMAIL,"请填写邮箱"));
            pwdEdit.setText(sharedPreferences.getString(PWD,"请填写密码"));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(emailEdit.getText())){
                    emailEdit.setError("此处不能为空！");
                }
                if(TextUtils.isEmpty(pwdEdit.getText())){
                    pwdEdit.setError("此处不能为空！");
                }else {
                    final String email = emailEdit.getText().toString();
                    final String pwd = pwdEdit.getText().toString();

                    String url = OkHttpManager.API_LOGIN+"?mail="+email+"&password="+pwd;
                    OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, IOException e) {
                            Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
                            Login login = GsonUtil.GsonToBean(result,Login.class);
                            Long status = login.getStatus();
                            String errorCode = login.getErrorCode();
                            com.akari.quark.entity.login.Message message = login.getMessage();
                            if(status==1){
                                Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
                                String token = message.getToken();
                                Intent intent = new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                                //写入SharedPreference
                                sharedPreferences.edit().putBoolean("AUTO_ISCHECK",true).commit();
                                sharedPreferences.edit().putString(EMAIL,email);
                                sharedPreferences.edit().putString(PWD,pwd);
                                sharedPreferences.edit().putString(TOKEN,token);
                                finish();
                            }else {
                                if(errorCode.equals("2002")){
                                    Toast.makeText(context,"密码错误",Toast.LENGTH_SHORT).show();
                                }
                                if(errorCode.equals("2004")){
                                    Toast.makeText(context,"该账户不存在",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    };
                    OkHttpManager.getAsyncNoHeader(url,dataCallBack);
                }

            }
        });
    }


}
