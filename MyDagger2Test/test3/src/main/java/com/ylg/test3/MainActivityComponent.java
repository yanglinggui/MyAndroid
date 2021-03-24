package com.ylg.test3;

import javax.inject.Singleton;

import dagger.Component;


@TestActivityScope
@Component(modules = MainActivityModule.class,dependencies = AppComponent.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
