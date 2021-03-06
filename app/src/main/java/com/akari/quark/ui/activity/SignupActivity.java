package com.akari.quark.ui.activity;

import android.content.Context;
import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    EditText _codeText;
    EditText _emailText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;
    private Context context;
    private String EMAIL = "";
    private String PWD = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = SignupActivity.this;

        _codeText = (EditText) findViewById(R.id.input_code);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

//        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
//                R.style.AppTheme_Login_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();

        String code = _codeText.getText().toString();
        EMAIL = _emailText.getText().toString();
        PWD = _passwordText.getText().toString();

        String url = "";
        Map<String, String> body = new HashMap<String, String>();

        if (code.isEmpty()) { //无邀请码
            url = OkHttpManager.API_SIGNUP;
            body.put("mail", EMAIL);
            body.put("password", PWD);
        } else { //有邀请码
            url = OkHttpManager.API_SIGNUP_WITH_INVITE;
            body.put("mail", EMAIL);
            body.put("password", PWD);
            body.put("invite", code);
        }

        OkHttpManager.DataCallBack callback = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Login login = GsonUtil.GsonToBean(result, Login.class);
                Long status = login.getStatus();
                String errorCode = login.getErrorCode();
                com.akari.quark.entity.login.Message message = login.getMessage();
                if (status == 1) {
                    onSignupSuccess();
                } else {
                    ErrorNotification.errorNotify(context, Integer.parseInt(errorCode));
                }
            }
        };
        OkHttpManager.postAsyncNoHeader(url, body, callback);



//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    public void onSignupSuccess() {
        Intent intent = new Intent();
        intent.putExtra("EMAIL", EMAIL);
        intent.putExtra("PASSWORD", PWD);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
//        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

//        String code = _codeText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("请输入一个有效的邮箱地址");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("密码不能为空");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}