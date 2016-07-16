package com.akari.quark.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.login.Login;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.ui.tool.ErrorNotification;
import com.akari.quark.util.GsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static final String ISCHECKED = "ischecked";
    private static final String EMAIL = "email";
    private static final String PWD = "pwd";
    private static final String TOKEN = "token";
    private Context context;
    private SharedPreferences sharedPreferences;

    EditText emailEdit;
    EditText pwdEdit;
    Button loginButton;
    TextView signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        sharedPreferences = getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);

        emailEdit = (EditText) findViewById(R.id.input_email);
        pwdEdit = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);

        emailEdit.setText(sharedPreferences.getString(EMAIL,""));
        pwdEdit.setText(sharedPreferences.getString(PWD,""));

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = emailEdit.getText().toString();
                final String pwd = pwdEdit.getText().toString();

                login(email, pwd);
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login(final String email, final String pwd) {
        Log.d(TAG, "Login");

        if (!validate(email, pwd)) {
            onLoginFailed();
            return;
        }

//        loginButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme_Login_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();

        String url = OkHttpManager.API_LOGIN;
        Map<String, String> body = new HashMap<String, String>();
        body.put("mail", email);
        body.put("password", pwd);

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
                    String token = message.getInfo();
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                    //写入SharedPreference
                    sharedPreferences.edit().putBoolean(ISCHECKED,true).commit();
                    sharedPreferences.edit().putString(EMAIL,email).commit();
                    sharedPreferences.edit().putString(PWD,pwd).commit();
                    sharedPreferences.edit().putString(TOKEN,token).commit();
                    finish();
                }else {
                    ErrorNotification.errorNotify(context, Integer.parseInt(errorCode));
                }

            }
        };
        OkHttpManager.postAsyncNoHeader(url, body, dataCallBack);

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra("EMAIL");
                String pwd = data.getStringExtra("PASSWORD");

                emailEdit.setText(email);
                pwdEdit.setText(pwd);

                login(email, pwd);

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
//                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        loginButton.setEnabled(true);
    }

    public boolean validate(String email, String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("请输入一个有效的邮箱地址");
            valid = false;
        } else {
            emailEdit.setError(null);
        }

        if (password.isEmpty()) {
            pwdEdit.setError("密码不能为空");
            valid = false;
        } else {
            pwdEdit.setError(null);
        }

        return valid;
    }
}
