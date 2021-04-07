package com.ylg.mydagger2study.qualifier;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Subcomponent;

@Subcomponent(modules = {MemoryModule.class, DiskModule.class, DeviceModule.class,
        /*DiskSetModule.class,*/ DeviceMapModule.class, AMDCPUModule.class,
        BlueToothModule.class})
public interface ComputerComponent {
    public void inject(Computer computer);
    BlueTooth getBlueTooth();

    @Subcomponent.Builder
    interface MyBuilder {
        ComputerComponent myBuild();
        /*@BindsInstance
        MyBuilder blueTooth(BlueTooth blueTooth);*/
        @BindsInstance
        MyBuilder blueToothVersion(@Named("BlueTooth") String version);
        MyBuilder memoryModule(MemoryModule memoryModule);
    }
}
