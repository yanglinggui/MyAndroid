package com.ylg.test3;

import javax.inject.Singleton;

import dagger.Component;

@TestActivityScope
@Component(modules = TestActivityMoudle.class,dependencies = AppComponent.class)
public interface TestActivityComponment {
    void inject(TestActivity testActivity);
}
