package com.akari.quark.network;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Akari on 16/5/23.
 */
public class NetworkTest {
    private static final String API_QUESTION_DETAIL = "/api/question/detail";
    private static final String X_ACCESS_TOKEN="x-access-token";
    private static final String TEMP_X_ACCESS_TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDY0MTkwOTc1NDYxfQ.5ejSZACMPlz3KXgQmBgINYYfgxULmEx2zVf-19TN34E";

    public static void main(String[] args){
        String url = OkHttpManager.BASE_URL+API_QUESTION_DETAIL;
        String urlDetail = OkHttpManager.attachHttpGetParam(url,"question_id","2");
        OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                System.out.print("ERROR");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                System.out.println(result);
            }
        };
        OkHttpManager.getAsync(urlDetail,dataCallBack,X_ACCESS_TOKEN,TEMP_X_ACCESS_TOKEN);
    }
}
