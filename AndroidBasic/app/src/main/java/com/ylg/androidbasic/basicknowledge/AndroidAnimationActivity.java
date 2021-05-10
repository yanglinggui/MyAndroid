package com.ylg.androidbasic.basicknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ylg.androidbasic.R;

public class AndroidAnimationActivity extends AppCompatActivity {

    public String title = "Android动画";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_animation);
        setTitle(title);
    }
}