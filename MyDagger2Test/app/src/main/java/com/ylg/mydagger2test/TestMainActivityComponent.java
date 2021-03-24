package com.ylg.mydagger2test;

import androidx.viewpager.widget.ViewPager;
import dagger.Component;

@Component(modules = TestMainActivityModule.class)
public interface TestMainActivityComponent {
    void inject(TestMainActivity testMainActivity);
}
