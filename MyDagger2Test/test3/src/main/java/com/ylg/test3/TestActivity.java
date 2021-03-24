package com.ylg.test3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class TestActivity extends Activity {

    @Inject
    SharedPreferences sp;

    @Inject
    People people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTestActivityComponment.builder().testActivityMoudle(new TestActivityMoudle(this)).appComponent(ComponentHolder.getMyApplicationAppComponent()).build().inject(this);
        android.util.Log.i("qiao", "TestActivity people = " + people.toString());
        //sp.edit().putString("ylg","test").commit();
    }
}
