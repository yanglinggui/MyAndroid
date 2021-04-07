package com.ylg.daggerdemo.application;

//import android.icu.text.SimpleDateFormat;

import com.ylg.daggerdemo.VirtualData;
import com.ylg.daggerdemo.oneA.OneActivityComponent;
import com.ylg.daggerdemo.twoA.TwoActivityComponent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import androidx.annotation.ColorInt;
import dagger.Module;
import dagger.Provides;

@Module/*(subcomponents = {OneActivityComponent.class, TwoActivityComponent.class})*/
public class ApplicationModule {
    @Qualifier
    public @interface ActivityColor {}

    @Qualifier
    public @interface ActivityData {}

    @Provides
    @ColorInt
    public int provideActivityColor(@ColorInt @ActivityColor int color) {
        return color;
    }

    @Provides
    @Singleton
    @ActivityData
    public VirtualData provideActivityData() {
        return new VirtualData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), new Random().nextInt());
    }
}
