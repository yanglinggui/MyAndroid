package com.ylg.daggerdemo.twoA;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

@Module
public class TwoActivityModule {
    @Qualifier
    public @interface Title {}

    @Provides
    @Title
    public String provideTitle() {
        return "Form Two Activity Module";
    }
}