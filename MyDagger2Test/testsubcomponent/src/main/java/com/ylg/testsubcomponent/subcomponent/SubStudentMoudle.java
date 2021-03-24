package com.ylg.testsubcomponent.subcomponent;

import com.ylg.testsubcomponent.bean.NewStudent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SubStudentMoudle {
    @Singleton
    @Provides
    public NewStudent provideStudent(){
        return new NewStudent();
    }
}
