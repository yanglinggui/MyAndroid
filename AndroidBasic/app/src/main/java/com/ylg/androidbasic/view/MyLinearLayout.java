package com.ylg.androidbasic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyLinearLayout extends LinearLayout {
    String TAG = "TouchEventDistribution";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "MyLinearLayout dispatchTouchEvent: MotionEvent.ACTION_DOWN");
        else if (ev.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "MyLinearLayout dispatchTouchEvent: MotionEvent.ACTION_UP");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "MyLinearLayout onTouchEvent: MotionEvent.ACTION_DOWN");
        else if (event.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "MyLinearLayout onTouchEvent: MotionEvent.ACTION_UP");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "MyLinearLayout onInterceptTouchEvent: MotionEvent.ACTION_DOWN");
        else if (ev.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "MyLinearLayout onInterceptTouchEvent: MotionEvent.ACTION_UP");
        return true;
        //return super.onInterceptTouchEvent(ev);
    }
}
