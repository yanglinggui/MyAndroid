package com.ylg.androidbasic.generalknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ylg.androidbasic.R;

public class ProcessAndThread extends AppCompatActivity {

    public String title = "进程和线程";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_and_thread);
        setTitle(title);
    }
}