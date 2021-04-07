package com.ylg.mydagger2study.qualifier;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BlueToothModule {

    @Provides
    BlueTooth provideBlueTooth(@Named("BlueTooth")String verson) {
        return new BlueTooth(verson);
    }
}
