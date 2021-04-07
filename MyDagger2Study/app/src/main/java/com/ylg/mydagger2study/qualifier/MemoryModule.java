package com.ylg.mydagger2study.qualifier;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

@Module
public class MemoryModule {

    private String vendor;
    private  int size;

    public MemoryModule(String vendor, int size) {
        this.vendor = vendor;
        this.size = size;
    }



    enum Vendor {Kingston, Samsung}
    @Qualifier
    public @interface MemoryType {
        int size() default 4096;
        Vendor vendor() default Vendor.Kingston;
    }

    @Provides
    @MemoryType
    public Memory provideMemory() {
        return new Memory(vendor,size);
    }

    @Provides
    @MemoryType(vendor = Vendor.Kingston,size = 4)
    public Memory provideKTMemory4G() {
        return new Memory("kingston",4);
    }

    @Provides
    @MemoryType(vendor = Vendor.Kingston,size = 8)
    public Memory provideKTMemory8G() {
        return new Memory("kingston",8);
    }

    @Provides
    @MemoryType(vendor = Vendor.Samsung,size = 4)
    public Memory provideSSMemory4G() {
        return new Memory("Samsung",4);
    }

    @Provides
    @MemoryType(vendor = Vendor.Samsung,size = 8)
    public Memory provideSSMemory8G() {
        return new Memory("Samsung",8);
    }
}
