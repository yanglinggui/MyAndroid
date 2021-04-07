package com.ylg.mydagger2study.qualifier;

import android.view.Window;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

@Module
public class ComputerModule {
    /*private String os;
    private int price;

    public ComputerModule(String os, int price){
        this.os = os;
        this.price = price;
    }

    @Provides
    public Computer provideComputer(){
        return new Computer(os, price);
    }*/

    private int windowsPrice;
    private int linuxPrice;

    public ComputerModule(int windowsPrice, int linuxPrice) {
        this.windowsPrice = windowsPrice;
        this.linuxPrice = linuxPrice;
    }

    @Qualifier
    public @interface windows {}

    @Qualifier
    public @interface linux {}

    @windows
    @Provides
    public Computer provideWindowsComputer(){
        return new WindowsComputer("windows", windowsPrice);
    }

    @linux
    @Provides
    public Computer provideLinuxComputer(){
        return new LinuxComputer("linux", linuxPrice);
    }

}
