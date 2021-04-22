package com.jslee.httpurlconnection;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OKHttpConnection {
    private OkHttpClient client;
    private static OKHttpConnection instance = new OKHttpConnection();
    public static OKHttpConnection getInstance() {
        return instance;
    }

    private OKHttpConnection(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void requestGetWebServer(String url, Callback callback) {

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }


    public void requestPostWebServer(String parameter, String url, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("parameter", parameter)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
