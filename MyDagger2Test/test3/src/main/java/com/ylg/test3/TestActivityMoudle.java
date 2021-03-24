package com.ylg.test3;

import dagger.Module;
import dagger.Provides;

@Module
public class TestActivityMoudle {

    private  TestActivity mTestActivity;

    public TestActivityMoudle(TestActivity mestActivity){
        mTestActivity = mestActivity;
    }

    @Provides
    People providePeople(){
        return  new People();
    }
}
