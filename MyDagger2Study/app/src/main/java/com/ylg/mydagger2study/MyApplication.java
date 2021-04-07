package com.ylg.mydagger2study;

import android.app.Application;

public class MyApplication extends Application {

    public ApplicationComponent applicationComponent;

    public static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule("MyDagger2Study")).build();
        myApplication = this;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }
}
