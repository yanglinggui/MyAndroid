package com.ylg.test3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    MainActivity mMainActivity;

    public MainActivityModule(MainActivity mainActivity){
        mMainActivity = mainActivity;
    }

    public MainActivityModule(){
        //mMainActivity = mainActivity;
    }

    @Provides
    @TestActivityScope
    People provideBook(){
        return  new People();
    }
}

