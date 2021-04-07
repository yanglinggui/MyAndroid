package com.ylg.mydagger2study.dagger2;

import java.util.Date;

public class TestInjector {
    public static void inject(TestActivity testActivity) {
        testActivity.computer = new Computer("Android", 3000, new CPU(), new Memory(12));
    }

    public static void inject1(TestActivity testActivity) {
        /*testActivity.computer = Computer_Factory.newInstance("AndroidGo", 699,
                CPU_Factory.newInstance(), Memory_Factory.newInstance(3));*/
        testActivity.computer = ComputerModule_ProvideComputerFactory.provideComputer(new ComputerModule("AndroidGo", 699,
                CPU_Factory.newInstance(), MemoryModule_ProvideMemoryFactory.provideMemory(new MemoryModule(3))));
    }


    public static void inject2(TestActivity testActivity) {
        //Computer computer = Computer_Factory.newInstance("inject2AndroidGo", 699,
         //       CPU_Factory.newInstance(), /*Memory_Factory.newInstance(3)*/MemoryModule_ProvideMemoryFactory.provideMemory(new MemoryModule(), 333));
        Date date = new Date(System.currentTimeMillis());
        date = DateModule_ProvideDateFactory.create(new DateModule()).get();

        //TestActivity_MembersInjector.injectComputer(testActivity, computer);
        TestActivity_MembersInjector.injectDate(testActivity, date);
    }
}
