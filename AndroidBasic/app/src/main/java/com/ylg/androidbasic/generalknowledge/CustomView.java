package com.ylg.androidbasic.generalknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ylg.androidbasic.R;

public class CustomView extends AppCompatActivity {

    public String title = "自定义View";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        setTitle(title);
    }
}