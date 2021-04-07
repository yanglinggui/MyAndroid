package com.ylg.daggerdemo.application;

import android.app.Activity;

import dagger.android.AndroidInjector;

public interface HasActivityInjector {
    AndroidInjector<Activity> activityInjector();
}
