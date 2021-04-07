package com.ylg.mydagger2study.dagger2;

import javax.inject.Inject;

public class Memory {
    private int size;

    //@Inject
    public Memory(int size){
        this.size = size;
    }

    public void execute(StringBuilder builder){
        builder.append("Memory Size: ").append(size).append("MB\n");
    }
}
