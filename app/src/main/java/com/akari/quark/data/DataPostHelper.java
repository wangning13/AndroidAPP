//package com.akari.quark.data;
//
//import com.google.common.net.HttpHeaders;
//
//import java.io.IOException;
//
//import okhttp3.RequestBody;
//
///**
// * Created by Akari on 16/5/27.
// */
//public class DataPostHelper {
//    public static int newTopic(String tag, String title, String content) throws ConnectionException, RemoteException {
//        LogUtils.v(TAG, "new topic in node: %s, title: %s", tag, title);
//
//        final String once = getOnceToken();
//        final RequestBody requestBody = new FormEncodingBuilder().add("once", once)
//                .add("title", title)
//                .add("content", content)
//                .build();
//
//        final Request request = newRequest().url(String.format(URL_NEW_TOPIC, tag))
//                .post(requestBody).build();
//        final Response response = sendRequest(request);
//
//        // v2ex will redirect if reply success
//        if (response.code() == HttpStatus.SC_MOVED_TEMPORARILY) {
//            final String location = response.header(HttpHeaders.LOCATION);
//            return Topic.getIdFromUrl(location);
//        }
//
//        final RequestException exception = new RequestException("post new topic failed", response);
//        try {
//            exception.setErrorHtml(TopicParser.parseProblemInfo(response.body().string()));
//        } catch (IOException e) {
//            throw new ConnectionException(e);
//        }
//        throw exception;
//    }
//
//}
