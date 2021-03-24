package com.ylg.testsubcomponent.subcomponent;

import com.ylg.testsubcomponent.bean.Teacher;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = SubStudentComponent.class)
public class SubTeacherModule {
    @Provides
    public Teacher provideFather(){
        return new Teacher();
    }
}
