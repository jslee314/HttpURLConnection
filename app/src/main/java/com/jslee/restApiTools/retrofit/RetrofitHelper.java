package com.jslee.restApiTools.retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public Bitmap requestGetSync(String base_url, String variable_url)  {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // 동기방식 ============
        Call<ResponseBody> request = retrofitService.getImageData(variable_url);
        try {
            ResponseBody responseBody = request.execute().body();
            final Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestGet(String base_url, String variable_url, RetrofitCallback retrofitCallback) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.getImageData(variable_url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                new Handler(Looper.getMainLooper()).post(() -> retrofitCallback.onSucceedRetrofit(bitmap));

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                new Handler(Looper.getMainLooper()).post(() -> retrofitCallback.onFailedRetrofit(t));

            }
        });
    }

    public interface RetrofitCallback {
        void onSucceedRetrofit(Bitmap bitmap);
        void onFailedRetrofit(Throwable throwable);
    }
}
