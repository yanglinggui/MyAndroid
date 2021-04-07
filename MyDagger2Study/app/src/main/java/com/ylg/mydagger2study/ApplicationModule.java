package com.ylg.mydagger2study;

import com.ylg.mydagger2study.qualifier.ComputerComponent;
import com.ylg.mydagger2study.qualifier.Device;
import com.ylg.mydagger2study.qualifier.Disk;
import com.ylg.mydagger2study.qualifier.Disk.*;
import com.ylg.mydagger2study.qualifier.QualiferActivityComponent;
import com.ylg.mydagger2study.qualifier.Device.*;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.LongKey;
import dagger.multibindings.StringKey;

@Module(subcomponents = {QualiferActivityComponent.class, ComputerComponent.class})
public class ApplicationModule {

    @Qualifier
    public @interface appname {}

    private String AppName;

    public ApplicationModule(String appname) {
        AppName = appname;
    }

    @Provides
    @appname
    public String getAppName() {
        return AppName;
    }

    /*@Provides
    @IntoSet
    public Disk prodieHH() {
        return new Disk(Type.HARD, Capacity.MARGE);
    }*/

    /*@Provides
    @IntoMap
    @StringKey("Sound")
    public Device provideSound() {
        return new Sound();
    }*/
}
