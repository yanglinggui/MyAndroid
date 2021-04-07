package com.ylg.mydagger2study;

import com.ylg.mydagger2study.qualifier.ComputerComponent;
import com.ylg.mydagger2study.qualifier.QualiferActivityComponent;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @ApplicationModule.appname
    public String getAppName();

    QualiferActivityComponent.Builder buildQualiferActivityComponent();
    ComputerComponent.MyBuilder buildComputerComponent();
}
