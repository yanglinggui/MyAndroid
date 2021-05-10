package com.ylg.androidbasic.generalknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ylg.androidbasic.R;

public class DataParse extends AppCompatActivity {

    public String title = "Android数据解析";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_parse);
        setTitle(title);


    }
}