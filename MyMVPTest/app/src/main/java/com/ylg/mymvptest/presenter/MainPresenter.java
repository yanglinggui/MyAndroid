package com.ylg.mymvptest.presenter;

import com.ylg.mymvptest.model.MainModel;
import com.ylg.mymvptest.model.MainModelBean;
import com.ylg.mymvptest.view.MainView;

public class MainPresenter implements IMainPresenter,Presenter<MainView> {

    private MainView mMainView;
    private MainModel mMainModel;

    public MainPresenter(MainView mainView){
        mMainView = mainView;
        attachView(mainView);
        mMainModel = new MainModel(this);
        android.util.Log.i("qiao","MainPresenter this : " + this.toString());
    }

    public void loadData() {
        mMainView.showProgress();
        mMainModel.loadData();
    }

    @Override
    public void loadDataSuccess(MainModelBean mainModelBean) {
        mMainView.showData(mainModelBean);
        mMainView.hideProgress();
    }

    @Override
    public void loadDataFailure() {
        mMainView.showData(null);
        mMainView.hideProgress();
    }

    @Override
    public void attachView(MainView view) {
        mMainView = view;
    }

    @Override
    public void detachView() {
        mMainView = null;
    }
}
