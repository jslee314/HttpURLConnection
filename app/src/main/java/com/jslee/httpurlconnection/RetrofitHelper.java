package com.jslee.httpurlconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitHelper {
    private static volatile RetrofitHelper INSTANCE;

    private Retrofit.Builder retrofitBuilder;

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


    public Bitmap initRetrofit(String url) {
//        url = url + "/";

        String base_url = "https://drive.google.com/";
        String variable_url = "uc?id=16Qqso9sZwe1UAcoXQX65wrokyb4_l3_J";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


        Call<ResponseBody> request = retrofitInterface.getImageData(variable_url);
        try {
            final Bitmap bitmap = BitmapFactory.decodeStream(request.execute().body().byteStream());
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
