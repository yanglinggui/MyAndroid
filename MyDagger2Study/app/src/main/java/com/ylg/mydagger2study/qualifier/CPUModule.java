package com.ylg.mydagger2study.qualifier;

import javax.inject.Named;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.BindsOptionalOf;
import dagger.Module;
import dagger.Provides;


class CPUModule {
   /* @Binds
    @Named("Intel")
    abstract CPU bindIntelCPU(Intel cpu);

    @Binds
    @Named("AMD")
    abstract CPU bindAMDCPU(AMD cpu);*/
}

@Module
abstract class IntelCPUModule {
    @Binds
    abstract CPU bindIntelCPU(Intel cpu);
}

@Module
abstract class AMDCPUModule {
    @Binds
    abstract CPU bindAMDCPU(AMD cpu);

   @Provides
    public static AMD provideAMDCPU() {
        return new AMD();
    }
}

/*@Module
abstract class OptionalCPUModule {
    @BindsOptionalOf
    abstract CPU optionalCPU();
}*/