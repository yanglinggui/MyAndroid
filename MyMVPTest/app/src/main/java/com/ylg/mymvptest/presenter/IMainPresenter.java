package com.ylg.mymvptest.presenter;

import com.ylg.mymvptest.model.MainModelBean;

public interface IMainPresenter {

    void loadDataSuccess(MainModelBean mainModelBean);

    void loadDataFailure();
}
