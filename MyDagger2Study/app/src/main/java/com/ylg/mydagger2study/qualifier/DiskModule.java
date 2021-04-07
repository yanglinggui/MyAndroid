package com.ylg.mydagger2study.qualifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;
import dagger.multibindings.Multibinds;

@Module
public class DiskModule {
    @Provides
    @IntoSet
    public Disk prodieSmallHD() {
        return new Disk(Disk.Type.HARD, Disk.Capacity.SMALL);
    }

    @Provides
    @IntoSet
    public Disk prodieSmallSD() {
        return new Disk(Disk.Type.SSD, Disk.Capacity.SMALL);
    }

    @Provides
    @ElementsIntoSet
    public Set<Disk> prodieSmallSHD() {
        return new HashSet<Disk>(Arrays.asList(new Disk(Disk.Type.SSD, Disk.Capacity.NORMAL)
                , new Disk(Disk.Type.HARD, Disk.Capacity.HUGE)
                , new Disk(Disk.Type.SSD, Disk.Capacity.HUGE)));
    }

   /* new Disk(Disk.Type.SSD, Disk.Capacity.NORMAL)
                , new Disk(Disk.Type.HARD, Disk.Capacity.HUGE)
                , new Disk(Disk.Type.SSD, Disk.Capacity.HUGE)*/
}


@Module
abstract class DiskSetModule{
    @Multibinds
    abstract Set<Disk> diskSet();
}