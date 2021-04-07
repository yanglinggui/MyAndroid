package com.ylg.mydagger2study.qualifier;

import com.ylg.mydagger2study.MyApplication;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

public  abstract class Computer {

    private String os;
    private int price;

    @Inject /*@Named("AMD")*/ CPU cpu;

   /* @Inject
    Optional<CPU> OptionalCPU;*/

    @Inject @MemoryModule.MemoryType(size = 4,vendor = MemoryModule.Vendor.Samsung)
    Memory memory;

    @Inject
    Set<Disk> disks;

    @Inject
    Map<String, Device> devices;

    @Inject
    Map<Long, Device> devices1;

    @Inject
    BlueTooth blueTooth;

    {
        /*DaggerComputerComponent.builder()
                .memoryModule(new MemoryModule("TN",3))
                //.blueTooth(new BlueTooth("5.0"))
                .blueToothVersion("4.0")
                .myBuild().inject(this);*/
        //DaggerComputerComponent.create().inject(this);
        MyApplication.getMyApplication().applicationComponent.buildComputerComponent()
                .blueToothVersion("4.0").memoryModule(new MemoryModule("TN", 10))
                .myBuild().inject(this);
    }

    public Computer(String os, int price) {
        this.os = os;
        this.price = price;
    }

    public void execute(StringBuilder builder) {
        builder.append("Qualifiter test Computer OS: " + os + "\n");
        builder.append("Qualifiter test Computer Price: " + price + "\n");
        /*if(OptionalCPU.isPresent()){ builder.append("i am OptionalCPU \n");OptionalCPU.get().execute(builder);}
        else builder.append("OptionalCPU is null\n");*/
        cpu.execute(builder);
        memory.execute(builder);
        disks.forEach(it -> it.mount(builder));
        devices.forEach((name, device) -> {builder.append(name + " : ");device.connect(builder);});

        devices1.forEach((name, device) -> {builder.append(name + " : ");device.connect(builder);});
        blueTooth.info(builder);
    }
}

class WindowsComputer extends Computer {
    public WindowsComputer(String os, int price) {
        super(os, price);
    }

    @Override
    public void execute(StringBuilder builder) {
        super.execute(builder);
    }
}

class LinuxComputer extends Computer {
    public LinuxComputer(String os, int price) {
        super(os, price);
    }

    @Override
    public void execute(StringBuilder builder) {
        super.execute(builder);
    }
}