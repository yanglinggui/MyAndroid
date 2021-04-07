package com.ylg.daggerdemo.oneA;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

@Module
public class OneActivityModule {
    @Qualifier
    public @interface Title {}

    @Provides
    @Title
    public String provideTitle() {
        return "Form One Activity Module";
    }
}