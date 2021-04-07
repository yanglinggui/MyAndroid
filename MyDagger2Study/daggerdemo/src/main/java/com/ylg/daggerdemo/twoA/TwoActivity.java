package com.ylg.daggerdemo.twoA;

import android.os.Bundle;

import android.util.Pair;
import android.widget.TextView;

import com.ylg.daggerdemo.R;
import com.ylg.daggerdemo.VirtualData;
import com.ylg.daggerdemo.application.ApplicationModule;
import com.ylg.daggerdemo.application.DemoApplication;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import dagger.android.AndroidInjection;

public class TwoActivity extends AppCompatActivity {

    @Inject
    @TwoActivityModule.Title
    String title;

    @Inject
    @ApplicationModule.ActivityColor
    int color;

    @Inject
    @ApplicationModule.ActivityData
    VirtualData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        /*((DemoApplication)(getApplication())).applicationComponent
                .newTwoActivityComponentBuilder()
                .buildTwoActivityComponent()
                .inject(this);*/
        AndroidInjection.inject(this);
        //Pair<String,Integer>[] pair =  new Pair[10];
        setTitle(title);
        findViewById(R.id.ctl_two).setBackgroundColor(color);
        ((TextView)findViewById(R.id.two)).setText(data.toString());
    }
}
