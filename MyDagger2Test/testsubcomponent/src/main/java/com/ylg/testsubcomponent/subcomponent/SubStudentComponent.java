package com.ylg.testsubcomponent.subcomponent;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = SubStudentMoudle.class)
public interface SubStudentComponent {

    void inject(SubcomponentActivity activity);
    @Subcomponent.Builder
    interface Builder{
        SubStudentComponent build();
    }
}
