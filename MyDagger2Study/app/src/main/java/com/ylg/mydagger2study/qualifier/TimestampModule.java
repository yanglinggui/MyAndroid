package com.ylg.mydagger2study.qualifier;

import java.util.Date;

import dagger.Module;
import dagger.Provides;

@Module
public class TimestampModule {
    @Provides
    public Date provideDate() {
        return new Date(System.currentTimeMillis());
    }
}
