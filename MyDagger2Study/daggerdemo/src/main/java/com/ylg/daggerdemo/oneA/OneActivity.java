package com.ylg.daggerdemo.oneA;

import android.os.Bundle;
import android.widget.TextView;

import com.ylg.daggerdemo.R;
import com.ylg.daggerdemo.VirtualData;
import com.ylg.daggerdemo.application.ApplicationModule;
import com.ylg.daggerdemo.application.DemoApplication;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OneActivity extends AppCompatActivity {

    @Inject
    @OneActivityModule.Title
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
        setContentView(R.layout.activity_one);

        ((DemoApplication)(getApplication())).applicationComponent
                .newOneActivityComponentBuilder()
                .buildOneActivityComponent()
                .inject(this);

        setTitle(title);
        findViewById(R.id.ctl_one).setBackgroundColor(color);
        ((TextView)findViewById(R.id.one)).setText(data.toString());
    }
}
