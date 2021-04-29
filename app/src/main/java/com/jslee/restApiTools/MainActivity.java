package com.jslee.restApiTools;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.jslee.restApiTools.okhttp.OKHttpConnection;
import com.jslee.restApiTools.retrofit.RetrofitHelper;
import com.jslee.restApiTools.urlConnection.ImageLoadTask;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3;
    ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String base_url = "https://drive.google.com/";
        String variable_url = "uc?id=16Qqso9sZwe1UAcoXQX65wrokyb4_l3_J";

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(v -> sendImageRequestByHttpUrlConnection(base_url, variable_url));

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(v -> sendImageRequestByOKHttp(base_url, variable_url));

        imageView3 = (ImageView) findViewById(R.id.imageView3);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            sendImageRequestByRetrofit(base_url, variable_url);
        });

    }

    /**
    * @내용 : urlConnection 을 통한 Rest api 통신
    * @수정 :
    * @버젼 : 0.0.0
    * @최초작성일 : 2021-04-29 오후 7:45
    * @작성자 : 이재선
    **/
    public void sendImageRequestByHttpUrlConnection(String base_url, String variable_url) {
        // 네트워크 통신 -> 작업스레드를 생성해서 호출
        ImageLoadTask task = new ImageLoadTask(base_url + variable_url, imageView1);
        task.execute();
    }

    /**
    * @내용 : okhttp 를 통한 Rest api 통신
    * @수정 :
    * @버젼 : 0.0.0
    * @최초작성일 : 2021-04-29 오후 7:45
    * @작성자 : 이재선
    **/
    private void sendImageRequestByOKHttp(String base_url, String variable_url){
        // 네트워크 통신 -> 작업스레드를 생성해서 호출
        new Thread() {
            public void run() {
                OKHttpConnection httpConn = OKHttpConnection.getInstance();
                httpConn.requestGetWebServer(base_url + variable_url, callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("jjslee", "콜백오류:"+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) {
            ResponseBody responseBody = response.body();
            final Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());

            // UI 조작 -> 다시 매인 스레드로
            new Handler(Looper.getMainLooper()).post(() -> imageView2.setImageBitmap(bitmap));

        }
    };

    private void sendImageRequestByRetrofit(String base_url, String variable_url){
        new Thread() {
            public void run() {
                final RetrofitHelper retrofitHelper = RetrofitHelper.getInstance();
                final Bitmap bitmap = retrofitHelper.initRetrofit(base_url, variable_url);

                // UI 조작 -> 다시 매인 스레드로
                runOnUiThread(() -> imageView3.setImageBitmap(bitmap));


            }
        }.start();
    }

}