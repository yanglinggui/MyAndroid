package com.ylg.androidbasic.basicknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.ylg.androidbasic.R;

public class DataStorageActivity extends AppCompatActivity {

    public static  String title = "Android五种数据存储方式";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        setTitle(title);
    }
}