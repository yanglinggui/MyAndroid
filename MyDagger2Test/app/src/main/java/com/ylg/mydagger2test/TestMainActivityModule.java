package com.ylg.mydagger2test;

import dagger.Module;
import dagger.Provides;

@Module
public class TestMainActivityModule {
    private TestMainActivity mTestMainActivity;

    public TestMainActivityModule(TestMainActivity testMainActivity){
        mTestMainActivity = testMainActivity;
    }

    @Provides
    Test providerA() {
        return new Test();
    }
}
