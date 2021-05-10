package com.ylg.androidbasic.generalknowledge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylg.androidbasic.R;

public class TouchEventDistribution extends AppCompatActivity {

    public String title = "Android事件分发机制";
    String TAG = "TouchEventDistribution";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_distribution);
        setTitle(title);
    }

    @Override
    public void onUserInteraction() {
        Log.i(TAG, "Activity onUserInteraction");
        super.onUserInteraction();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "Activity dispatchTouchEvent: MotionEvent.ACTION_DOWN");
        else if (ev.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "Activity dispatchTouchEvent: MotionEvent.ACTION_UP");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            Log.i(TAG, "Activity onTouchEvent: MotionEvent.ACTION_DOWN");
        else if (event.getAction() == MotionEvent.ACTION_UP)
            Log.i(TAG, "Activity onTouchEvent: MotionEvent.ACTION_UP");
        return super.onTouchEvent(event);
    }
}