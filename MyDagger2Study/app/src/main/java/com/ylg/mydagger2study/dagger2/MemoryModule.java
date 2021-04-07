package com.ylg.mydagger2study.dagger2;

import dagger.Module;
import dagger.Provides;

@Module
public class MemoryModule {

    private  int size;

    public MemoryModule(int size) {
        this.size = size;
    }

    @Provides
    /*public Memory provideMemory(int size) {
        return new Memory(size);
    }*/
    public Memory provideMemory() {
        return new Memory(size);
    }
}
