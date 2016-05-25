package com.akari.quark.data;

import com.akari.quark.entity.asksInMain.AsksInMain;
import com.akari.quark.entity.asksInMain.AsksInMainMessage;
import com.akari.quark.network.OkHttpManager2;
import com.akari.quark.util.GsonUtil;

import java.util.List;

/**
 * Created by Akari on 16/5/25.
 */
public class DataRequestHelper {
    public static final String BASE_URL = "http://115.159.160.18:3000";

    private static final String API_GET_ALL_ASK_QUESTIONS = BASE_URL + "/api/question/list/ask";
    private static final String API_GET_ALL_ANSWER_QUESTIONS = BASE_URL + "/api/question/list/answer";
    private static final String API_LOGIN = BASE_URL + "/api/user/login";
    private static final String API_QUESTION_DETAIL = BASE_URL + "/api/question/detail";
    private static final String API_ADD_QUESTION = BASE_URL + "/api/question/add";
    private static final String API_WATCH_QUESTION = BASE_URL + "/api/question/focus";
    private static final String API_ANSWER_DETAIL = BASE_URL + "/api/answer/detail";
    private static final String API_ADD_ANSWER = BASE_URL + "/api/answer/add";

    private static final String X_ACCESS_TOKEN="x-access-token";
    private static final String TEMP_X_ACCESS_TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0MTkwOTc1NDYxfQ.5ejSZACMPlz3KXgQmBgINYYfgxULmEx2zVf-19TN34E";

    public static List<AsksInMainMessage> getAsksInMain(int page){
        final String url = OkHttpManager2.attachHttpGetParam(API_GET_ALL_ASK_QUESTIONS,"page",page+"");
        final String result = OkHttpManager2.getSyncString(url,X_ACCESS_TOKEN,TEMP_X_ACCESS_TOKEN);
        AsksInMain asksInMain = GsonUtil.GsonToBean(result,AsksInMain.class);
        List<AsksInMainMessage> messageList = asksInMain.getMessage();
        return messageList;
    }
}
