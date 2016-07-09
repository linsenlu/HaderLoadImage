package com.example.administrator.haderloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String path = "http://pic.58pic.com/10/20/29/99bOOOPIC77.jpg";
    ImageView imageView;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==111){
                Bitmap bitmap = (Bitmap) msg.obj;
                imageView.setImageBitmap(bitmap);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void downLoad(View view){
        try {
            new Thread(){
                @Override
                public void run() {
                    URL url = null;
                    try {
                        url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        final Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                        Log.e("print","Success");
                        /*
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });*/
//                      Message对象虽然可以通过构造器来创建，但是出于效率考虑不建议这么做。
//                      而是调用Message的静态方法obtain()(多个重载)来获得Message对象
                        Message msg = Message.obtain();
                        msg.what = 111;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
