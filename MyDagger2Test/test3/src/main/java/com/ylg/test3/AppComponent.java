package com.ylg.test3;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    SharedPreferences SharedPreferences();

    MyApplication myApplication();

    //MainActivityComponent addMainActivityComponent(MainActivityModule mainActivityModule);
}
