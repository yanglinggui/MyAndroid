package com.ylg.test3;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    MyApplication myApplication;

    public AppModule(MyApplication application) {
        myApplication = application;
    }


    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(){
        return myApplication.getSharedPreferences("myDagger2Test", Context.MODE_PRIVATE);
    }

    @Provides
    MyApplication providesMyApplication(){
        return myApplication;
    }
}
