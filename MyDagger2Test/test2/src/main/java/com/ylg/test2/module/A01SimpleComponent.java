package com.ylg.test2.module;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by QingMei on 2017/6/11 21:32
 * email:mei_husky@qq.com
 * desc:Used to
 */
@Singleton
@Component(modules = A01SimpleModule.class)
public interface A01SimpleComponent {

    void inject1(A01SimpleActivity activity);

}
