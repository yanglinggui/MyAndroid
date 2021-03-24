package com.ylg.test2.module;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ylg.test2.R;
import com.ylg.test2.bean.Student;

import javax.inject.Inject;



public class A01SimpleActivity extends AppCompatActivity {




    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a01_simple);
        //新添代码
        DaggerA01SimpleComponent.builder()
                .a01SimpleModule(new A01SimpleModule(this))
                .build()
                .inject1(this);
        student.toString();
    }


}
