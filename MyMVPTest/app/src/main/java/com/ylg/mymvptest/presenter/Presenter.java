package com.ylg.mymvptest.presenter;

public interface Presenter<V> {

    void  attachView(V view);

    void detachView();
}
