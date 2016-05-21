package com.akari.quark.network;

import android.util.Log;

import com.akari.quark.common.exception.ConnectionException;
import com.akari.quark.common.exception.RemoteException;
import com.akari.quark.common.exception.RequestException;
import com.akari.quark.util.AppCtx;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Akari on 16/5/21.
 */
public class RequestHelper {
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

    private static final OkHttpClient CLIENT;

    static {
        CLIENT = new OkHttpClient();
        CLIENT.newBuilder().cache(buildCache());
        CLIENT.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        CLIENT.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        CLIENT.newBuilder().readTimeout(30, TimeUnit.SECONDS);
        CLIENT.newBuilder().followRedirects(false);
    }

    private static Cache buildCache() {
        final File cacheDir = AppCtx.getInstance().getCacheDir();
        final int cacheSize = 64 * 1024 * 1024;

        return new Cache(cacheDir, cacheSize);
    }

    static Request.Builder newRequest() {
        Request.Builder builder = new Request.Builder();
        builder.header(X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);

        return builder;
    }

    static OkHttpClient getClient() {
        return CLIENT;
    }

//    public static List<Topic> getTopics(Page page) throws ConnectionException, RemoteException {
//        final Request request = newRequest()
//                .url(page.getUrl())
//                .build();
//
//        final Response response = sendRequest(request);
//        if (response.isRedirect()) {
//            throw new ExIllegalStateException("topics page should not redirect");
//        }
//
//        final Document doc;
//        final List<Topic> topics;
//        try {
//            doc = Parser.toDoc(response.body().string());
//            processUserState(doc, page instanceof Tab ? PageType.Tab : PageType.Node);
//            topics = TopicListParser.parseDoc(doc, page);
//        } catch (IOException e) {
//            throw new ConnectionException(e);
//        }
//
//        if (BuildConfig.DEBUG) {
//            Log.v(TAG, "received topics, count: " + topics.size());
//        }
//
//        return topics;
//    }

    public static String getStringFromServer(String url) throws ConnectionException, RemoteException, IOException{
        final Request request = newRequest().url(url).build();
        final Response response = sendRequest(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    private static final String CHARSET_NAME = "UTF-8";
//    /**
//     * 这里使用了HttpClinet的API。只是为了方便
//     * @param params
//     * @return
//     */
//    public static String formatParams(List<BasicNameValuePair> params){
//        return URLEncodedUtils.format(params, CHARSET_NAME);
//    }
//    /**
//     * 为HttpGet 的 url 方便的添加多个name value 参数。
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params){
//        return url + "?" + formatParams(params);
//    }
    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value){
        return url + "?" + name + "=" + value;
    }

    static Response sendRequest(Request request) throws ConnectionException, RemoteException {
        return sendRequest(request, true);
    }

    static Response sendRequest(Request request, boolean checkResponse) throws ConnectionException, RemoteException {
        final Response response;
        try {
            response = CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
        if (checkResponse) {
            checkResponse(response);
        }

        return response;
    }

    private static void checkResponse(Response response) throws RemoteException, RequestException, ConnectionException {
        if (response.isSuccessful()) {
            return;
        }

        final int code = response.code();

        throw new RequestException(response);
    }

    public static void main(String[] args) throws ConnectionException, RemoteException, IOException {
        RequestHelper h=new RequestHelper();
        String str=h.getStringFromServer(h.attachHttpGetParam(API_GET_ALL_ASK_QUESTIONS,"page","0"));
        System.out.println(str);
    }
}