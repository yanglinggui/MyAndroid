package com.ylg.test3;

public class ComponentHolder {
    private static AppComponent myApplicationAppComponent;

    public static AppComponent getMyApplicationAppComponent() {
        return myApplicationAppComponent;
    }

    public static void setMyApplicationAppComponent(AppComponent myApplicationAppComponent) {
        ComponentHolder.myApplicationAppComponent = myApplicationAppComponent;
    }
}
