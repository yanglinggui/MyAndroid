package com.ylg.mydagger2study.qualifier;

public class BlueTooth {

    private String info;

    public BlueTooth(String info) {
        this.info = info;
    }

    public void info(StringBuilder builder) {
        builder.append("Bluetooth Version: ").append(info).append("\n");
    }

}
