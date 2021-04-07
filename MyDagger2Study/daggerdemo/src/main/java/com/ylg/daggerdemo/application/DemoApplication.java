package com.ylg.daggerdemo.application;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class DemoApplication extends Application implements HasActivityInjector,HasAndroidInjector {

    public ApplicationComponent applicationComponent;

    @Inject
    DispatchingAndroidInjector<Activity> activityInjectorDispatcher;

    @Inject
    DispatchingAndroidInjector<Object> objectAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .activityColor(Color.BLUE)
                .buildApplicationComponent();
        applicationComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjectorDispatcher;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return objectAndroidInjector;
    }

    public DispatchingAndroidInjector<Activity> getActivityInjectorDispatcher() {
        return activityInjectorDispatcher;
    }
}
