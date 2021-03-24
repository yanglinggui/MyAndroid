package com.ylg.mymvptest.view;

import com.ylg.mymvptest.model.MainModelBean;

public interface MainView {
    void showData(MainModelBean mainModelBean);

    void showProgress();

    void hideProgress();
}
