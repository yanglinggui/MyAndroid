package com.ylg.androidbasic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyTextView extends TextView {

    String TAG = "TouchEventDistribution";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "MyTextView onTouchEvent: MotionEvent.ACTION_DOWN");
        else if (event.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "MyTextView onTouchEvent: MotionEvent.ACTION_UP");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "MyTextView dispatchTouchEvent: MotionEvent.ACTION_DOWN");
        else if (event.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "MyTextView dispatchTouchEvent: MotionEvent.ACTION_UP");
        return super.dispatchTouchEvent(event);
    }

}
