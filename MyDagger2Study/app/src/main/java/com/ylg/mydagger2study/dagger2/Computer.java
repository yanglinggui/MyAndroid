package com.ylg.mydagger2study.dagger2;

import java.time.chrono.MinguoEra;

import javax.inject.Inject;

import dagger.Component;

public class Computer {

    private CPU cpu;
    private Memory memory;
    private String os;
    private int price;

    //@Inject
    public Computer(String os, int price, CPU cpu, Memory memory){
        this.os = os;
        this.price = price;
        this.cpu = cpu;
        this.memory = memory;
    }

    public void execute(StringBuilder builder){
        builder.append("Computer OS: ").append(os).append("\n");
        builder.append("Comuter Price: ").append(price).append("\n");
        cpu.execute(builder);
        memory.execute(builder);
    }
}
