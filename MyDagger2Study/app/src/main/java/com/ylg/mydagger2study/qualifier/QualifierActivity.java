package com.ylg.mydagger2study.qualifier;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Provides;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.ylg.mydagger2study.DaggerApplicationComponent;
import com.ylg.mydagger2study.MyApplication;
import com.ylg.mydagger2study.R;
import com.ylg.mydagger2study.dagger2.CPU;
import com.ylg.mydagger2study.dagger2.Memory;
import com.ylg.mydagger2study.dagger2.TestInjector;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;

public class QualifierActivity extends AppCompatActivity {

    @Inject
    @ComputerModule.windows
    Computer windows;

    @Inject
    @ComputerModule.linux
    Computer linux;

    @Inject
    Date date;

    @Inject
    Provider<Date> providerDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView tv = findViewById(R.id.test_tv);
       /* QualiferActivityComponent component = DaggerQualiferActivityComponent.builder()
                .computerModule(new ComputerModule(888,999))
                .monitorModule(new MonitorModule(this, tv))
                .timestampModule(new TimestampModule())
                .applicationComponent(((MyApplication)getApplication()).applicationComponent)
                .build();
        component.inject(this);*/

        QualiferActivityComponent component = ((MyApplication)getApplication()).applicationComponent
                .buildQualiferActivityComponent()
                .computerModule(new ComputerModule(123,456))
                .monitorModule(new MonitorModule(this, tv))
                .build();
        component.inject(this);
        StringBuilder stb = new StringBuilder();

        windows.execute(stb);
        linux.execute(stb);
        stb.append(date.toString() + "\n" );
        //tv.setText(stb.toString());
        component.getMonitor().show(linux);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                stb.append(providerDate.get().toString() + "\n");
                //tv.setText(stb.toString());
                component.getMonitor().startRefresh(providerDate.get());
                handler.postDelayed(this, 1000);
            }
        },1000);

        tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                component.getMonitor().toastInfo();
                component.getMonitor().toastAppname(component.getAppName());
            }
        });
    }
}