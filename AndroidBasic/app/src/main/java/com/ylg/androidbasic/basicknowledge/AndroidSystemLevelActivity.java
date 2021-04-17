package com.ylg.androidbasic.basicknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ylg.androidbasic.R;

public class AndroidSystemLevelActivity extends AppCompatActivity {

    public static  String title = "Android系统架构";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_system_level);
        setTitle(title);
    }
}