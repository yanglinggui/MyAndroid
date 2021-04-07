package com.ylg.mydagger2study.qualifier;

public interface Device {
    public void connect(StringBuilder builder);
}

class Keyboard implements Device {
    @Override
    public void connect(StringBuilder builder) {
        builder.append("keyboard connect \n");
    }
}

class Mouse implements Device {
    @Override
    public void connect(StringBuilder builder) {
        builder.append("Mouse connect \n");
    }
}

class Sound implements Device {
    @Override
    public void connect(StringBuilder builder) {
        builder.append("Sound connect \n");
    }
}