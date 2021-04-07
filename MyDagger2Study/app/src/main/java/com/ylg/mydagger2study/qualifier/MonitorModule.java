package com.ylg.mydagger2study.qualifier;

import android.content.Context;
import android.widget.TextView;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

@Module
public class MonitorModule {

    private Context context;
    private TextView textView;


    @Scope
    public @interface MonitorScope{}

    public MonitorModule(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @MonitorScope
    @Provides
    public Monitor provideMonitor() {
        return new Monitor(context, textView);
    }
}
