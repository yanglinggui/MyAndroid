package com.ylg.androidbasic.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.ylg.androidbasic.R;

public class TouchEvent extends AppCompatActivity {

    String TAG = "TouchEvent";
    public static String title = "TouchEvent 测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*final int historySize = event.getHistorySize();
        final int pointerCount = event.getPointerCount();
        for (int h = 0; h < historySize; h++) {
            Log.i(TAG, "getHistoricalEventTime: " + event.getHistoricalEventTime(h));
            for (int p = 0; p < pointerCount; p++) {
                Log.i(TAG, "getPointerId: " + event.getPointerId(p) + " ,getX= " + event.getX(p) + " ,getY= " + event.getY(p));
            }
        }
        Log.i(TAG, "getEventTime: " + event.getEventTime());
        for (int p = 0; p < pointerCount; p++) {
            Log.i(TAG, "getPointerId: " + event.getPointerId(p) + " ,getX= " + event.getX(p) + " ,getY= " + event.getY(p));
        }*/

        int action = MotionEventCompat.getActionMasked(event);
        int index;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                index = event.getActionIndex();
                Log.i(TAG, "ACTION_DOWN getPointerId: " + event.getPointerId(index));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                index = event.getActionIndex();
                Log.i(TAG, "ACTION_POINTER_DOWN getPointerId: " + event.getPointerId(index));
                break;
            case MotionEvent.ACTION_UP:
                index = event.getActionIndex();
                Log.i(TAG, "ACTION_UP getPointerId: " + event.getPointerId(index));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                index = event.getActionIndex();
                Log.i(TAG, "ACTION_POINTER_UP getPointerId: " + event.getPointerId(index));
                break;
        }
        return super.onTouchEvent(event);
    }
}