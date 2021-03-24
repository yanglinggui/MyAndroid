package com.ylg.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    People people;

    @Inject
    People people1;

    @Inject
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainActivityComponent.builder().mainActivityModule(new MainActivityModule(this)).appComponent(ComponentHolder.getMyApplicationAppComponent()).build().inject(this);

        android.util.Log.i("qiao", "MainActivity people = " + people.toString() + " , people1 = " + people1.toString());

        //android.util.Log.i("qiao", " sp = " + sp.toString());
        //sp.edit().putString("ylg","MainActivity test").commit();
    }

    public void myButton(View view) {
        startActivity(new Intent(this,TestActivity.class));
    }
}