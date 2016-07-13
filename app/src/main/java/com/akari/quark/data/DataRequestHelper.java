package com.akari.quark.data;

import android.util.Log;

import com.akari.quark.entity.Token;
import com.akari.quark.entity.answersInMain.AnswersInMain;
import com.akari.quark.entity.answersInMain.AnswersInMainMessage;
import com.akari.quark.entity.asksInMain.AsksInMain;
import com.akari.quark.entity.asksInMain.AsksInMainMessage;
import com.akari.quark.entity.comment.Comment;
import com.akari.quark.entity.comment.CommentMessage;
import com.akari.quark.network.OkHttpManager2;
import com.akari.quark.util.GsonUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Akari on 16/5/25.
 */
public class DataRequestHelper {
    public static final String BASE_URL = "http://115.159.160.18:3000";

    private static final String API_GET_ALL_ASK_QUESTIONS = BASE_URL + "/api/question/list/ask";
    private static final String API_GET_ALL_ANSWER_QUESTIONS = BASE_URL + "/api/question/list/answer";
    private static final String API_LOGIN = BASE_URL + "/api/account/login";
    private static final String API_QUESTION_DETAIL = BASE_URL + "/api/question/detail";
    private static final String API_ADD_QUESTION = BASE_URL + "/api/question/add";
    private static final String API_WATCH_QUESTION = BASE_URL + "/api/question/focus";
    private static final String API_ANSWER_DETAIL = BASE_URL + "/api/answer/detail";
    private static final String API_ADD_ANSWER = BASE_URL + "/api/answer/add";
    private static final String API_GET_COMMENTS = BASE_URL+ "/api/comment/list";

    private static final String MAIL_PARAM = "mail";
    private static final String PASSWORD_PARAM = "password";
    private static final String ANSWER_ID_PARAM = "answer_id";
    private static final String PAGE_PARAM = "page";

    private static final String ACCOUNT_MAIL = "919169204@qq.com";
    private static final String ACCOUNT_PASSWORD = "123456";

    private static final String X_ACCESS_TOKEN = "x-access-token";
    private static final String TEMP_X_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0ODUyNzE2MDExfQ.1sJDUeBZS0O1-Tjru2V05K8SJTPWB_D5weRuUEL1Upw";

    private static final String eg = "{\"message\": [ {\"id\": 1, \"bestAnswer\": { \"praise_num\": 91, \"content\": \"金属和配体的摩尔比，温度，升降温速率、反应时间，反应液的PH，其实反应液的PH才是晶体最敏感的，不断的尝试，看一些好一点儿外文文献，仿照着做，坚持不懈肯定会有好结果滴~\", \"answerer_id\": 1, \"create_time\": 1463069669, \"avatar_url\": \"default.png\" }, \"title\": \"单晶小宝宝怎么养？\", \"topics\": [ \"化学\", \"无机化学\" ]},{ \"id\": 1, \"bestAnswer\": { \"praise_num\": 91, \"content\": \"金属和配体的摩尔比，温度，升降温速率、反应时间，反应液的PH，其实反应液的PH才是晶体最敏感的，不断的尝试，看一些好一点儿外文文献，仿照着做，坚持不懈肯定会有好结果滴~\", \"answerer_id\": 1, \"create_time\": 1463069669, \"avatar_url\": \"default.png\" }, \"topics\": [ \"化学\", \"无机化学\" ], \"title\": \"单晶小宝宝怎么养？\" } ], \"status\": 1 }";

    public static List<AsksInMainMessage> getAsksInMain(int page) {
        final String url = OkHttpManager2.attachHttpGetParam(API_GET_ALL_ASK_QUESTIONS, "page", page + "");
        final String result = OkHttpManager2.getSyncString(url, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
        AsksInMain asksInMain = GsonUtil.GsonToBean(result, AsksInMain.class);
        List<AsksInMainMessage> messageList = asksInMain.getMessage();
        Log.d("GET", url);
        return messageList;
    }

    public static List<AnswersInMainMessage> getAnswersInMain(int page) {
        final String url = OkHttpManager2.attachHttpGetParam(API_GET_ALL_ANSWER_QUESTIONS, "page", page + "");
        final String result = OkHttpManager2.getSyncString(url, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
        AnswersInMain answersInMain = GsonUtil.GsonToBean(result, AnswersInMain.class);
        List<AnswersInMainMessage> messageList = answersInMain.getMessage();
        Log.d("GET", url);
        return messageList;
    }

    public static Comment getComments(long answerID, int page) {
        List<BasicNameValuePair> paramList = new LinkedList<BasicNameValuePair>();
        paramList.add(new BasicNameValuePair(ANSWER_ID_PARAM, "1"));
        paramList.add(new BasicNameValuePair(PAGE_PARAM, page+""));
        String url = OkHttpManager2.attachHttpGetParams(API_GET_COMMENTS, paramList);
        Log.d("GET1", url);
        final String result = OkHttpManager2.getSyncString(url, X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);
        Comment comments = GsonUtil.GsonToBean(result, Comment.class);
        Log.d("GET2", url);
        return comments;
    }

    public static String getToken(String mail, String password) {
        List<BasicNameValuePair> paramList = new LinkedList<BasicNameValuePair>();
        paramList.add(new BasicNameValuePair(MAIL_PARAM, mail));
        paramList.add(new BasicNameValuePair(PASSWORD_PARAM, password));
        String url = OkHttpManager2.attachHttpGetParams(API_LOGIN, paramList);
        final String result = OkHttpManager2.getSyncString(url, null, null);
        String token = GsonUtil.GsonToBean(result, Token.class).getToken();
        Log.d("TOKEN", token);
        return token;
    }

    public static void main(String[] args) {
        String a = getToken(ACCOUNT_MAIL, ACCOUNT_PASSWORD);
        System.out.println(a);
    }
}