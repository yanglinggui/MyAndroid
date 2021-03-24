package com.ylg.testsubcomponent.Dependencies;

import com.ylg.testsubcomponent.bean.NewStudent;

import dagger.Module;
import dagger.Provides;
@Module
public class DStudentModule {
    @Provides
    NewStudent provideStudent(){
        return new NewStudent();
    }
}
