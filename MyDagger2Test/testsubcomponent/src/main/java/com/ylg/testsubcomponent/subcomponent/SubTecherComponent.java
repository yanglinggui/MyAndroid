package com.ylg.testsubcomponent.subcomponent;

import dagger.Component;

@Component(modules = SubTeacherModule.class)
public interface SubTecherComponent {
    SubStudentComponent.Builder buildStudentComponent();
}
