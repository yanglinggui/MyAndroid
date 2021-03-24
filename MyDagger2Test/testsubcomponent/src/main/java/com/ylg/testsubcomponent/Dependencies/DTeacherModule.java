package com.ylg.testsubcomponent.Dependencies;

import com.ylg.testsubcomponent.bean.Teacher;

import dagger.Module;
import dagger.Provides;

@Module
public class DTeacherModule {
    @Provides
    Teacher provideTeacher(){
        return new Teacher();
    }
}
