package com.ylg.mydagger2study.dagger2;

import java.util.Random;

import javax.inject.Inject;

public class CPU {

    @Inject
    public CPU(){

    }

    public void execute(StringBuilder builder){
        builder.append("CPU Id: ").append(new Random().nextInt()).append("\n");
    }
}
