package com.akari.quark.network;

import android.os.Handler;
import android.os.RemoteException;

import com.akari.quark.util.AppCtx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpManager {
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

    //okhttpclient实例
    private static final OkHttpClient mClient;

    //因为我们请求数据一般都是子线程中请求，在这里我们使用了handler
    private static Handler mHandler;

    //构造方法
//    private OkHttpManager() {
//
//        mClient = new OkHttpClient();
//
//        //在这里直接设置连接超时.读取超时，写入超时
//        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
//        mClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
//        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
//
//        //如果是用的3.0之前的版本  使用以下直接设置连接超时.读取超时，写入超时
//
//        //client.setConnectTimeout(10, TimeUnit.SECONDS);
//        //client.setWriteTimeout(10, TimeUnit.SECONDS);
//        //client.setReadTimeout(30, TimeUnit.SECONDS);
//
//
//    }

    static {
        mClient = new OkHttpClient();
//        mClient.newBuilder().cache(buildCache());
        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().readTimeout(30, TimeUnit.SECONDS);
        mClient.newBuilder().followRedirects(false);

//        初始化handler
//        mHandler = new Handler(Looper.getMainLooper());

    }

    //建立缓存
    private static Cache buildCache() {
        final File cacheDir = AppCtx.getInstance().getCacheDir();
        final int cacheSize = 64 * 1024 * 1024;

        return new Cache(cacheDir, cacheSize);
    }

    //使用token进行访问
    static Request.Builder newRequest() {
        Request.Builder builder = new Request.Builder();
        builder.header(X_ACCESS_TOKEN, TEMP_X_ACCESS_TOKEN);

        return builder;
    }

    static OkHttpClient getClient() {
        return mClient;
    }

    //-------------------------同步的方式请求数据--------------------------
    public static void main(String[] args) throws ConnectException,IOException,RemoteException{
        String response = getSyncString(
                "http://115.159.160.18:3000/api/question/detail?question_id=1",
                X_ACCESS_TOKEN,TEMP_X_ACCESS_TOKEN);
        System.out.print(response);
    }

    //同步GET，返回Response
    public static Response getSync(String url,String key,String token) {

        //通过获取到的实例来调用内部方法
        return inner_getSync(url,key,token);
    }

    //同步GET返回Response内部逻辑
    private static Response inner_getSync(String url,String key,String token) {
        Request request = new Request.Builder()
                .url(url)
                .header(key,token)
                .build();
        Response response = null;
        try {
            //同步请求返回的是response对象
            response = mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    //同步GET返回String
    public static String getSyncString(String url,String key,String token) {
        return inner_getSyncString(url,key,token);
    }


    //同步GET返回String内部逻辑
    private static String inner_getSyncString(String url,String key,String token) {
        String result = null;
        try {
            /**
             * 把取得到的结果转为字符串，这里最好用string()
             */
            result = inner_getSync(url,key,token)
                    .body()
                    .string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //-------------------------异步的方式请求数据--------------------------
    //异步GET
    public static void getAsync(String url, DataCallBack callBack,String key,String token) {
        inner_getAsync(url, callBack,key,token);
    }

    //异步GET内部逻辑
    private static void inner_getAsync(String url, final DataCallBack callBack,String key,String token) {
        final Request request = new Request.Builder()
                .header(key,token)
                .url(url)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                }
                deliverDataSuccess(result, callBack);
            }
        });
    }


    /**
     * 分发失败的时候调用
     *
     * @param request
     * @param e
     * @param callBack
     */
    private static void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack) {
        /**
         * 在这里使用异步处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(request, e);
                }
            }
        });
    }

    /**
     * 分发成功的时候调用
     *
     * @param result
     * @param callBack
     */
    private static void deliverDataSuccess(final String result, final DataCallBack callBack) {
        /**
         * 在这里使用异步线程处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.requestSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        void requestFailure(Request request, IOException e);

        void requestSuccess(String result) throws Exception;
    }

    //-------------------------提交表单--------------------------
    //异步POST

    public static void postAsync(String url, Map<String, String> params, DataCallBack callBack) {
        inner_postAsync(url, params, callBack);
    }

    private static void inner_postAsync(String url, Map<String, String> params, final DataCallBack callBack) {

        RequestBody requestBody = null;
        if (params == null) {
            params = new HashMap<>();
        }

        /**
         * 如果是3.0之前版本的，构建表单数据是下面的一句
         */
        //FormEncodingBuilder builder = new FormEncodingBuilder();

        /**
         * 3.0之后版本
         */
        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
         */
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formbody中
             */
            builder.add(key, value);
        }
        requestBody = builder.build();
        //结果返回
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                deliverDataSuccess(result, callBack);
            }


        });
    }


    //-------------------------文件下载--------------------------
    public static void downloadAsync(String url, String desDir, DataCallBack callBack) {
        inner_downloadAsync(url, desDir, callBack);
    }

    /**
     * 下载文件的内部逻辑处理类
     *
     * @param url      下载地址
     * @param desDir   目标地址
     * @param callBack
     */
    private static void inner_downloadAsync(final String url, final String desDir, final DataCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                /**
                 * 在这里进行文件的下载处理
                 */
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    //文件名和目标地址
                    File file = new File(desDir, getFileName(url));
                    //把请求回来的response对象装换为字节流
                    inputStream = response.body().byteStream();
                    fileOutputStream = new FileOutputStream(file);
                    int len = 0;
                    byte[] bytes = new byte[2048];
                    //循环读取数据
                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }
                    //关闭文件输出流
                    fileOutputStream.flush();
                    //调用分发数据成功的方法
                    deliverDataSuccess(file.getAbsolutePath(), callBack);
                } catch (IOException e) {
                    //如果失败，调用此方法
                    deliverDataFailure(request, e, callBack);
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }

                }
            }

        });
    }

    /**
     * 根据文件url获取文件的路径名字
     *
     * @param url
     * @return
     */
    private static String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        String path = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
        return path;
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
}