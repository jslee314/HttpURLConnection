
package com.jslee.restApiTools.okhttp;

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

    /** Post 요청 */
    public void requestPost(String parameter, String url, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("parameter", parameter)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /** Get 요청 */
    public void requestGet(String url, Callback callback) {

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }



    /** Post 요청 */


    /** Delete 요청 */
}
