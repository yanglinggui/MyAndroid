package com.ylg.daggerdemo.twoA;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = TwoActivityModule.class)
public interface TwoActivityComponent extends AndroidInjector<TwoActivity> {
    /*public void inject(TwoActivity activity);

    @Subcomponent.Builder
    interface Builder {
        TwoActivityComponent buildTwoActivityComponent();
    }*/

    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<TwoActivity>{};
}
