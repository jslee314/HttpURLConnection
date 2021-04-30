package com.jslee.restApiTools.urlConnection;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.net.URL;

public class HttpURLConnHelper {
    private static HttpURLConnHelper instance = new HttpURLConnHelper();
    public static HttpURLConnHelper getInstance() {
        return instance;
    }

    private HttpURLConnHelper(){  }


    /** Get 요청 */
    public void requestGet(String urlStr, URLConnCallback urlConnCallback) {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(urlStr);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    new Handler(Looper.getMainLooper()).post(() -> urlConnCallback.onSucceedUrlConn(bitmap));
                } catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(() -> urlConnCallback.onFailedUrlConn(e));
                }
            }
        }.start();
    }

    public interface URLConnCallback {
        void onSucceedUrlConn(Bitmap bitmap);
        void onFailedUrlConn(Throwable throwable);
    }


}