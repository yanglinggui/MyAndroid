package com.ylg.mydagger2study.qualifier;

import javax.inject.Inject;

public class Memory {
    private String vendor;
    private int size;

    //@Inject
    public Memory(String vendor, int size){
        this.vendor = vendor;
        this.size = size;
    }

    public void execute(StringBuilder builder){
        builder.append("Qualifier test Memory vendor: ").append(vendor).append("\n");
        builder.append("Qualifier test Memory Size: ").append(size).append("MB\n");
    }
}
