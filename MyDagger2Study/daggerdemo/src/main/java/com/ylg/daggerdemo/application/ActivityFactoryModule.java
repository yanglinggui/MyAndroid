package com.ylg.daggerdemo.application;

import android.app.Activity;

import com.ylg.daggerdemo.twoA.TwoActivity;
import com.ylg.daggerdemo.twoA.TwoActivityComponent;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {TwoActivityComponent.class})
abstract class ActivityFactoryModule {

    @Binds
    @IntoMap
    @ClassKey(TwoActivity.class)
    abstract AndroidInjector.Factory<?> bindTwoActivityInjectorFactory(TwoActivityComponent.Factory factory);
}
