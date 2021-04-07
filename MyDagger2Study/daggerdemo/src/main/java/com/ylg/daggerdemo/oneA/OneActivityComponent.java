package com.ylg.daggerdemo.oneA;

import dagger.Subcomponent;

@Subcomponent(modules = OneActivityModule.class)
public interface OneActivityComponent {
    public void inject(OneActivity activity);

    @Subcomponent.Builder
    interface Builder {
        OneActivityComponent buildOneActivityComponent();
    }
}
