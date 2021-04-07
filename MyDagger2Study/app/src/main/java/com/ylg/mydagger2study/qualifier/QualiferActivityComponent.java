package com.ylg.mydagger2study.qualifier;

import com.ylg.mydagger2study.ApplicationComponent;
import com.ylg.mydagger2study.ApplicationModule;

import dagger.Component;
import dagger.Subcomponent;

@MonitorModule.MonitorScope
//@Component(modules = {ComputerModule.class, TimestampModule.class, MonitorModule.class},dependencies = ApplicationComponent.class)
@Subcomponent(modules = {ComputerModule.class, TimestampModule.class, MonitorModule.class})
public interface QualiferActivityComponent {
    public void inject(QualifierActivity qualifierActivity);

    Monitor getMonitor();
    @ApplicationModule.appname
    public String getAppName();

    @Subcomponent.Builder
    interface Builder{
        QualiferActivityComponent build();
        Builder monitorModule(MonitorModule monitorModule);
        Builder computerModule(ComputerModule computerModule);
    }
    /*@Component.Builder
    interface Builder {
        Builder applicationComponent(ApplicationComponent applicationComponent);
    }*/

    /*@Component.Builder
    interface Builder {
        Builder monitorModule(MonitorModule monitorModule);
        Builder computerModule(ComputerModule computerModule);
        //Builder myComputerModule(ComputerModule computerModule);
        QualiferActivityComponent build();
    }*/
}
