package com.ylg.mydagger2study.qualifier;

import java.util.Random;

import javax.inject.Inject;

public  class CPU {

    /*@Inject
    public CPU() {

    }*/

    public void execute(StringBuilder builder){
        builder.append("Qualifier test CPU Id: ").append(new Random().nextInt()).append("\n");
    }
}


class Intel extends CPU {

    @Inject
    public Intel() {

    }

    @Override
    public void execute(StringBuilder builder) {
        builder.append("I am Intel: ");
        super.execute(builder);
    }
}

class AMD extends CPU {

    //@Inject
    public AMD() {

    }

    @Override
    public void execute(StringBuilder builder) {
        builder.append("I am AMD: ");
        super.execute(builder);
    }
}