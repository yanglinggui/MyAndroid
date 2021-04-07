package com.ylg.mydagger2study.dagger2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.os.Bundle;
import android.widget.TextView;

import com.ylg.mydagger2study.R;

import java.util.Date;

import javax.inject.Inject;

public class TestActivity extends AppCompatActivity {

    @Inject
    Computer computer;

    @Inject
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView tv = findViewById(R.id.test_tv);
        StringBuilder stb = new StringBuilder();
        computer = new Computer("Linux", 6500, new CPU(), new Memory(16));
        TestInjector.inject(this);
        TestInjector.inject1(this);
        TestInjector.inject2(this);
        computer.execute(stb);
        stb.append(date.toString() + "\n");
        tv.setText(stb.toString());
        
    }
}