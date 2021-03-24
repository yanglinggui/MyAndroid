package com.ylg.testsubcomponent.Dependencies;

import com.ylg.testsubcomponent.bean.Teacher;

import dagger.Component;
import dagger.Module;

@Component(modules = DTeacherModule.class)
public interface DTeacherComponent {
    Teacher getTeacher();

}
