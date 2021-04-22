package com.jslee.httpurlconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitHelper {
    private static volatile RetrofitHelper INSTANCE;

    public static RetrofitHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitHelper();
                }
            }
        }
        return INSTANCE;
    }

    public Bitmap initRetrofit(String base_url, String variable_url)  {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseBody> request = retrofitInterface.getImageData(variable_url);
        try {
            ResponseBody responseBody = request.execute().body();
            final Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
