package com.ylg.mydagger2study.qualifier;

public class Disk {

    public enum Type {HARD, SSD}
    public enum Capacity{
        SMALL(256), NORMAL(512), HUGE(1024), MARGE(2018);
        private int szie;
        Capacity(int size) {
            this.szie = size;
        }
    }

    private Type type;
    private Capacity capacity;

    public Disk(Type type, Capacity capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public void mount(StringBuilder builder){
        builder.append(capacity.szie + "G, " + type.name() + " mounted\n");
    }
}
