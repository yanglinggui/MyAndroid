package com.ylg.test2.module;

import com.ylg.test2.bean.Student;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by QingMei on 2017/6/11 21:33
 * email:mei_husky@qq.com
 * desc:Used to
 */
@Module
public class A01SimpleModule {

    private A01SimpleActivity activity;

    public A01SimpleModule(A01SimpleActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    Student provideStudent(){
        return new Student();
    }
}
