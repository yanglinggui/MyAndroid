package com.ylg.mydagger2study.dagger2;

import dagger.Module;
import dagger.Provides;

@Module
public class ComputerModule {

    private CPU cpu;
    private Memory memory;
    private String os;
    private int price;

    public ComputerModule(String os, int price, CPU cpu, Memory memory){
        this.os = os;
        this.price = price;
        this.cpu = cpu;
        this.memory = memory;
    }

    @Provides
    public Computer provideComputer() {
        return new Computer(os, price, cpu, memory);
    }
}
