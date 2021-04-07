package com.ylg.mydagger2study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ylg.mydagger2study.dagger2.TestActivity;
import com.ylg.mydagger2study.qualifier.QualifierActivity;

import javax.inject.Provider;

//https://blog.csdn.net/u012273376/article/details/90296577
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 设置为无标题(去掉Android自带的标题栏)，(全屏功能与此无关)
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置为全屏模式
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.btn_qualifier:
                startActivity(new Intent(this, QualifierActivity.class));
                break;
        }
    }
}