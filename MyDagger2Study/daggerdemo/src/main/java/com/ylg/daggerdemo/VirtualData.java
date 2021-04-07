package com.ylg.daggerdemo;

public class VirtualData {
    private String name;
    private int num;

    public VirtualData(String name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public String toString() {
        return super.toString() + " ,VirtualData{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
