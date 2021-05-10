package com.ylg.androidbasic.basicknowledge;

import android.os.Bundle;

import com.ylg.androidbasic.R;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutActivity extends AppCompatActivity {
    public String title = "常用布局";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        setTitle(title);
    }
}
