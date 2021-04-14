package com.ylg.myretrofittest;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://blog.csdn.net/m0_37796683/article/details/90702095
 */
public class MainActivity extends AppCompatActivity {

    String TAG = "qiao.retrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl("https://api.uomg.com/")
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //创建网络请求接口对象实例
        Api api = retrofit.create(Api.class);

        Call<Object> call = api.postDataCall("JSON");

        Log.e(TAG, "request url：" + call.request().url());

        StringBuilder sb = new StringBuilder();
        if (call.request().body() instanceof FormBody) {
            FormBody body = (FormBody) call.request().body();
            for (int i = 0; i < body.size(); i++) {
                sb.append(body.encodedName(i))
                        .append(" = ")
                        .append(body.encodedValue(i))
                        .append(",");
            }
           // sb.delete(sb.length() - 1, sb.length());
            Log.e(TAG, "| RequestParams:{" + sb.toString() + "}");
        }

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //步骤8：请求处理,输出结果
                Object body = response.body();
                if (body == null) return;
                Log.i(TAG,"onResponse success：" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.i(TAG,"onFailure: " + t.getMessage());
            }
        });
        /*Call<ResponseBody> responseBodyCall = api.getTestData("新歌榜", "json");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                Log.i(TAG,"onResponse success: " + response.body().string());
                } catch (Exception e) {
                    Log.i(TAG,"onResponse success error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG,"onFailure: " + t.getMessage());
            }
        });
        //对发送请求进行封装
        /*Call<Data<Info>> dataCall = api.getJsonData("新歌榜", "json");

        // 同步执行
        // Response<Data<Info>> execute = jsonDataCall.execute();

        //异步请求
        dataCall.enqueue(new Callback<Data<Info>>() {
            //请求成功回调
            @Override
            public void onResponse(Call<Data<Info>> call, Response<Data<Info>> response) {
                Data<Info> body = response.body();
                if (body == null) return;
                //Log.i(TAG,"返回的数据：info: " + );
                Info info = body.getData();
                if (info == null) return;
                Log.i(TAG,"返回的数据：info: " + info.getName() + ", url: " + info.getPicurl());
            }
            //请求失败回调
            @Override
            public void onFailure(Call<Data<Info>> call, Throwable t) {
                Log.i(TAG, "回调失败：" + t.getMessage() + ", " + t.toString());
            }
        });*/
    }
}