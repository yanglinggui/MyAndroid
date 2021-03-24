package com.ylg.testsubcomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ylg.testsubcomponent.Dependencies.DependenciesAcvitity;
import com.ylg.testsubcomponent.subcomponent.SubcomponentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SubcomponentActivity.class));
    }
}