package com.ylg.mymvptest.model;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ylg.mymvptest.presenter.IMainPresenter;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainModel {

    IMainPresenter mIMainPresenter;

    public MainModel(IMainPresenter mIMainPresenter) {
        this.mIMainPresenter = mIMainPresenter;
    }

    public void loadData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("http://www.weather.com.cn/adat/sk/101010100.html",new JsonHttpResponseHandler(
        ){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    MainModelBean mainModelBean = new MainModelBean();
                    JSONObject weatherinfo = response.getJSONObject("weatherinfo");
                    mainModelBean.setCity(weatherinfo.getString("city"));
                    mainModelBean.setWd(weatherinfo.getString("WD"));
                    mainModelBean.setWs(weatherinfo.getString("WS"));
                    mainModelBean.setTime(weatherinfo.getString("time"));
                    mIMainPresenter.loadDataSuccess(mainModelBean);
                    android.util.Log.i("qiao","loadData: " + response.toString());
                }catch (Exception e){
                    android.util.Log.i("qiao","loadData Exception: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                try {
                    mIMainPresenter.loadDataFailure();
                    android.util.Log.i("qiao","loadData fail: " + errorResponse.toString());
                }catch (Exception e){
                    android.util.Log.i("qiao","loadData onFailure: " + e.getMessage());
                }

            }
        });
    }
}
