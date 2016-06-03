package com.akari.quark.data;

import com.akari.quark.common.exception.ConnectionException;
import com.akari.quark.common.exception.RemoteException;
import com.akari.quark.entity.createQuestion.QuestionCreate;
import com.akari.quark.network.OkHttpManager2;
import com.akari.quark.util.GsonUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Akari on 16/5/27.
 */
public class DataPostHelper {
    public static final String BASE_URL = "http://115.159.160.18:3000";

    public static final String API_ADD_QUESTION = BASE_URL + "/api/question/add";
    public static final String API_ADD_ANSWER = BASE_URL + "/api/answer/add";

    private static final String MAIL_PARAM = "mail";
    private static final String PASSWORD_PARAM = "password";

    private static final String ACCOUNT_MAIL = "919169204@qq.com";
    private static final String ACCOUNT_PASSWORD = "123456";

    public static final String X_ACCESS_TOKEN = "x-access-token";
    public static final String TEMP_X_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0ODUyNzE2MDExfQ.1sJDUeBZS0O1-Tjru2V05K8SJTPWB_D5weRuUEL1Upw";

    public static QuestionCreate newQuestion(String title, String description, String tag) throws ConnectionException, RemoteException, IOException {
        final RequestBody requestBody = new FormBody.Builder()
                .add("question_title", title)
                .add("description", description)
                .add("tags", tag)
                .build();

        final Request request = OkHttpManager2.newRequest(X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN).url(API_ADD_QUESTION)
                .post(requestBody).build();
        final Response response = OkHttpManager2.sendRequest(request);
        QuestionCreate create = GsonUtil.GsonToBean(response.body().string(), QuestionCreate.class);
        return create;
    }

    public static void main(String[] args) throws ConnectionException, RemoteException, IOException {
        QuestionCreate create = newQuestion("蛤蛤", "蛤蛤蛤", "1-6");
        System.out.println(create.getMessage());
    }
}
