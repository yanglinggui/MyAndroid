package com.ylg.testsubcomponent.Dependencies;

import com.ylg.testsubcomponent.bean.NewStudent;

import dagger.Component;

@Component(modules = DStudentModule.class, dependencies = DTeacherComponent.class)
public interface DStudentComponent {
    void inject(DependenciesAcvitity activity);
}
