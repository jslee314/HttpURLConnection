package com.jslee.restApiTools;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jslee.restApiTools.data.Code;
import com.jslee.restApiTools.okhttp.OKHttpConnection;
import com.jslee.restApiTools.retrofit.RetrofitHelper;
import com.jslee.restApiTools.urlConnection.HttpURLConnHelper;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Getter
@Setter
public class MainViewModel extends AndroidViewModel implements HttpURLConnHelper.URLConnCallback , RetrofitHelper.RetrofitCallback {

    private MutableLiveData<Code.NETWORKING> getBitmap = new MutableLiveData<>();
    private Bitmap bitmap;

    private String base_url;
    private String variable_url;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        base_url = "https://drive.google.com/";
        variable_url = "uc?id=16Qqso9sZwe1UAcoXQX65wrokyb4_l3_J";
    }


    public void onBtnClicked(Code.NETWORKING networkingMode){
        switch (networkingMode) {
            case UrlConnection:
                sendImageRequestByHttpUrlConnection();
                break;

            case OKHttp:
                sendImageRequestByOKHttp();
                break;

            case Retrofit:
                //sendImageRequestByRetrofitSync();
                sendImageRequestByRetrofit();
                break;
        }
    }

    /**
     * @내용 : urlConnection 을 통한 Rest api 통신
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-04-29 오후 7:45
     * @작성자 : 이재선
     **/
    public void sendImageRequestByHttpUrlConnection() {
        // 네트워크 통신 -> 작업스레드를 생성해서 호출
        String urlStr = base_url + variable_url;

        HttpURLConnHelper urlConn = HttpURLConnHelper.getInstance();
        urlConn.requestGet(urlStr, this);

    }
    @Override
    public void onSucceedUrlConn(Bitmap bitmap) {
        this.bitmap = bitmap;
        getBitmap.setValue(Code.NETWORKING.UrlConnection);
    }

    @Override
    public void onFailedUrlConn(Throwable throwable) {
        Log.d("jjslee", "콜백오류:"+throwable.getMessage());

    }

    /**
     * @내용 : okhttp 를 통한 Rest api 통신
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-04-29 오후 7:45
     * @작성자 : 이재선
     **/
    private void sendImageRequestByOKHttp(){
        // 네트워크 통신 -> 작업스레드를 생성해서 호출
        new Thread() {
            public void run() {
                OKHttpConnection httpConn = OKHttpConnection.getInstance();
                httpConn.requestGet(base_url + variable_url, okHttpCallback);
            }
        }.start();
    }

    private final Callback okHttpCallback = new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("jjslee", "콜백오류:"+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) {
            ResponseBody responseBody = response.body();
            bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
            new Handler(Looper.getMainLooper()).post(() -> getBitmap.setValue(Code.NETWORKING.OKHttp));

        }
    };

    /**
    * @내용 :
    * @수정 :
    * @버젼 : 0.0.0
    * @최초작성일 : 2021-04-29 오후 11:29
    * @작성자 : 이재선
    **/
    // 동기 처리
    private void sendImageRequestByRetrofitSync() {
        new Thread() {
            public void run() {
                RetrofitHelper retrofitHelper = RetrofitHelper.getInstance();

                bitmap = retrofitHelper.requestGetSync(base_url, variable_url);
                new Handler(Looper.getMainLooper()).post(() -> getBitmap.setValue(Code.NETWORKING.Retrofit));

            }
        }.start();
    }

    // 비동기 처리
    private void sendImageRequestByRetrofit() {
        RetrofitHelper retrofitHelper = RetrofitHelper.getInstance();
        retrofitHelper.requestGet(base_url, variable_url, this);

    }


    @Override
    public void onSucceedRetrofit(Bitmap bitmap) {
        this.bitmap = bitmap;
        new Handler(Looper.getMainLooper()).post(() -> getBitmap.setValue(Code.NETWORKING.Retrofit));

    }

    @Override
    public void onFailedRetrofit(Throwable throwable) {
        Log.d("jjslee", "콜백오류:"+throwable.getMessage());

    }
}
