package com.ylg.mydagger2study.dagger2;

import java.util.Date;
import javax.inject.Inject;

public class TestDate {

    public Date date;

    @Inject
    public final void setTimestamp( Date date) {
        this.date = date;
    }
}
