package com.ylg.mymvptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ylg.mymvptest.model.MainModelBean;
import com.ylg.mymvptest.presenter.MainPresenter;
import com.ylg.mymvptest.view.MainView;


//学习资料http://www.jishudog.com/8978/html

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {


    ProgressBar mProgressBar;

    TextView mTextView;

    Button mButton;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.prg);
        mTextView = findViewById(R.id.tv);
        mButton = findViewById(R.id.bt);
        mButton.setOnClickListener(this);
        android.util.Log.i("qiao","MainActivity this : " + this.toString());
        mainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void showData(MainModelBean mainModelBean) {
        String text = mainModelBean==null ? "load fail" : mainModelBean.getCity() + " \n " + mainModelBean.getWd()
                + " \n " + mainModelBean.getWs() + " \n " + mainModelBean.getTime();
        mTextView.setText(text);
    }

    @Override
    public void showProgress() {
        android.util.Log.i("qiao","showProgress");
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        android.util.Log.i("qiao","onClick");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainPresenter.loadData();
            }
        }, 5000);
    }
}