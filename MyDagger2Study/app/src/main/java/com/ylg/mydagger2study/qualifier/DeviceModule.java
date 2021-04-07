package com.ylg.mydagger2study.qualifier;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.LongKey;
import dagger.multibindings.Multibinds;
import dagger.multibindings.StringKey;

@Module
public class DeviceModule {

    @Provides
    @IntoMap
    @StringKey("Mouse")
    public Device provideMouse() {
        return  new Mouse();
    }

    @Provides
    @IntoMap
    @StringKey("Keyboard")
    public Device provideKeyboard() {
        return new Keyboard();
    }

    @Provides
    @IntoMap
    @LongKey(1L)
    public Device provideSound() {
        return new Sound();
    }
}

@Module
abstract class DeviceMapModule{
    @Multibinds
    abstract Map<String ,Device> deviceMap();
    @Multibinds
    abstract Map<Long ,Device> deviceLongMap();
}