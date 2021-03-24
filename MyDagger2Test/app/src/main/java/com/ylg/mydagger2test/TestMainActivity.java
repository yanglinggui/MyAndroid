package com.ylg.mydagger2test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

//学习参看https://blog.csdn.net/mq2553299/article/details/77485800
public class TestMainActivity extends AppCompatActivity {


    @Inject
    Test myTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        //DaggerA01SimpleComponent.builder() .a01SimpleModule(new A01SimpleModule(this)) .build() .inject(this);
        //DaggerTestMainActivityComponent.create().inject(this);
        DaggerTestMainActivityComponent.builder()
                //.testMainActivityModule(new TestMainActivityModule(this))
                .build().inject(this);
    }

    public void btnClick(View view) {
        Toast.makeText(TestMainActivity.this,myTest.toString(),Toast.LENGTH_SHORT).show();
    }
}