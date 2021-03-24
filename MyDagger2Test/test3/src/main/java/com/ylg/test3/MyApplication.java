package com.ylg.test3;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        ComponentHolder.setMyApplicationAppComponent(mAppComponent);
       // mAppComponent.addMainActivityComponent(new MainActivityModule()).inject(this);
    }
}
