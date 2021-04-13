package com.ylg.myokhttptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/m0_37796683/article/details/101029208
 */
public class MainActivity extends AppCompatActivity {

    String TAG = "qiao.OkHttpClient";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testOkHttpClient();
        //testHeader();
        //testGET();
        testPOST();
    }

    private OkHttpClient getOkHttpClient() {

        //方式一：创建OkHttpClient实例,使用默认构造函数，创建默认配置OkHttpClient(官方建议全局只有一个实例)
        OkHttpClient okHttpClient = new OkHttpClient();

        //方式二：通过new OkHttpClient.Builder() 一步步配置一个OkHttpClient实例
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(13, TimeUnit.SECONDS).build();

        //方式三：如果要求使用现有的实例，可以通过newBuilder().build()方法进行构造
        OkHttpClient client = okHttpClient.newBuilder().build();

        return okHttpClient;
    }

    private void testOkHttpClient() {
        //1.构建OkHttpClient实例
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)//链接超时为2秒，单位为秒
                .writeTimeout(2, TimeUnit.SECONDS)//写入超时
                .readTimeout(2, TimeUnit.SECONDS)//读取超时
                .build();

        //2.通过Builder辅助类构建请求对象
        final Request request = new Request.Builder()
                .url("http://httpbin.org/delay/10")//URL地址
                .build();//构建

        //创建线程，在子线程中运行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //3.通过mOkHttpClient调用请求得到Call
                    final Call call = okHttpClient.newCall(request);
                    //4.执行同步请求，获取响应体Response对象
                    Response response = call.execute();
                    Log.e(TAG, "请求(超时)==" + response);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "请求ERROR: " + e.toString());
                }
            }
        }).start();
    }

    private void testHeader(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)//链接超时为2秒，单位为秒
                .writeTimeout(2, TimeUnit.SECONDS)//写入超时
                .readTimeout(2, TimeUnit.SECONDS)//读取超时
                .build();

        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")//设置唯一值
                .addHeader("Server", "application/json; q=0.5")//设置新值
                .addHeader("Server", "application/vnd.github.v3+json")//设置新值
                .build();
        Log.e(TAG, "request=" + request.toString());
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "Post请求(HTTP头)异步响应failure==" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e(TAG, "header：Date==" + response.header("Date"));
                Log.e(TAG, "header：User-Agent==" + response.header("User-Agent"));
                Log.e(TAG, "headers：headers Server==" + response.headers("Server"));
                Log.e(TAG, "headers：header Server==" + response.header("Server"));
                Log.e(TAG, "headers：Vary==" + response.headers("Vary"));

                Log.e(TAG, "Post请求(HTTP头)异步响应Success==" + response.body().string());
            }
        });
    }

    private void testGET(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)//链接超时为2秒，单位为秒
                .writeTimeout(2, TimeUnit.SECONDS)//写入超时
                .readTimeout(2, TimeUnit.SECONDS)//读取超时
                .build();

        Request request = new Request.Builder()
                .get()
                .url("https://www.baidu.com")
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "get enqueue fail: " +  e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //Log.e(TAG, "get enqueue fail: " +  e.getMessage());
                //主线程中更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO 在主线程中更新UI的操作
                    }
                });

                Log.i(TAG, "get enqueue 线程id: " + Thread.currentThread().getId());
                Log.i(TAG, "get enqueue " + response.body().string());
                if (response.headers() != null) {
                    for (int i = 0; i < response.headers().size(); i++) {
                        Log.i(TAG, "get enqueue " + i + " " +response.headers().name(i) + " : " + response.headers().value(i));
                    }
                }
            }
        });

        //call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();

                    if (response.isSuccessful()) {
                        Log.e(TAG, "get同步请求success==" + response.body().string());
                    }

                    if (response.headers() != null) {
                        for (int i = 0; i < response.headers().size(); i++) {
                            Log.i(TAG, "get请求头== " + i + response.headers().name(i) + " : " + response.headers().value(i));
                        }
                    }
                } catch (Exception e) {
                    Log.i(TAG, "get Error e: " + e.getMessage());
                }
            }
        }).start();
    }

    private void testPOST(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody requestBody = RequestBody.create("testPOST提交的内容", mediaType);

        verifyStoragePermissions(this);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"),
                //new File("/storage/emulated/0/Android/data/com.ylg.myokhttptest/files/text.txt"));
                new File("/sdcard/text.txt"));

        Log.i(TAG,"testPOST FilePath: " + getExternalFilesDirs(null).length);
        FormBody formBody = new FormBody.Builder()
                //添加键值对(通多Key-value的形式添加键值对参数)
                .add("search", "Jurassic Park")
                .build();

        Request request = new Request.Builder()
                //.post(requestBody)
                .post(requestBody1)
                .url("https://api.github.com/markdown/raw")
                .build();

        Request request1 = new Request.Builder()
                //.url("https://en.wikipedia.org/w/index.php")
                .url("https://api.github.com/markdown/raw")
                .post(formBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Response response = okHttpClient.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.i(TAG,"testPOST isSuccessful: " + response.body().string());
                    }else{
                        Log.i(TAG,"testPOST fail: " + response.body().string());
                    }
                }catch (Exception e) {
                    Log.i(TAG,"testPOST Exception: " + e.getMessage());
                }
            }
        }).start();

        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "Post请求(表单)异步响应failure==" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "Post请求(表单)异步响应Success==" + result);
            }
        });
    }
}