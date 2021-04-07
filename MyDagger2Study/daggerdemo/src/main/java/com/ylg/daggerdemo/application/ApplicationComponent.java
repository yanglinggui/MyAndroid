package com.ylg.daggerdemo.application;

import com.ylg.daggerdemo.oneA.OneActivityComponent;
import com.ylg.daggerdemo.twoA.TwoActivityComponent;

import javax.inject.Singleton;

import androidx.annotation.ColorInt;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class,  AndroidInjectionModule.class,ActivityFactoryModule.class})
public interface ApplicationComponent {
    public OneActivityComponent.Builder newOneActivityComponentBuilder();
   // public TwoActivityComponent.Builder newTwoActivityComponentBuilder();

    public void inject(DemoApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        public Builder activityColor(@ColorInt @ApplicationModule.ActivityColor int color);

        public ApplicationComponent buildApplicationComponent();
    }
}
