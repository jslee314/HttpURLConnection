package com.jslee.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private OKHttpConnection httpConn = OKHttpConnection.getInstance();

    Button button1;
    ImageView imageView1;

    Button button2;
    ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://drive.google.com/uc?id=16Qqso9sZwe1UAcoXQX65wrokyb4_l3_J";

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImageRequestByHttpUrlConnection(url);
            }
        });

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImageRequestByOKHttp(url);
            }
        });

    }

    public void sendImageRequestByHttpUrlConnection(String url) {
        // 네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출 해줄 것!!
        ImageLoadTask task = new ImageLoadTask(url, imageView1);
        task.execute();
    }

    private void sendImageRequestByOKHttp(String url){

        // 네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출 해줄 것!!
        new Thread() {
            public void run() {
                httpConn.requestGetWebServer(url, callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("jjslee", "콜백오류:"+e.getMessage());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            // 매인쓰레드로(UI 조작해야되니) 다시~
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    imageView2.setImageBitmap(bitmap);
                }
            });
        }
    };
}