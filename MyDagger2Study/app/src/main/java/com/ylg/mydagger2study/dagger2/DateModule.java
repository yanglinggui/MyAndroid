package com.ylg.mydagger2study.dagger2;

import java.util.Date;

import dagger.Module;
import dagger.Provides;

@Module
public class DateModule {

    @Provides
    public Date provideDate(){
        return new Date(System.currentTimeMillis());
    }
}
