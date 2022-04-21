package com.ylg.mywindowinsetstest;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Insets;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.animation.DecelerateInterpolator;

import java.util.Arrays;
import java.util.List;

import static android.view.WindowInsetsAnimation.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;
import static android.view.WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE;
import static android.view.WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH;
import static android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE;

public class MainActivity extends Activity {

    private static String TAG = "yanglg";

    Window window;
    View rootView;
    WindowInsetsController controller;
    WindowInsets windowInsets;
    private static final int WRITE_PERMISSION = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        rootView = window.getDecorView();
        controller = rootView.getWindowInsetsController();
        //window.setDecorFitsSystemWindows(false);
        //Cursor
        //controller.show(WindowInsets.Type.ime());
        //controller.hide(WindowInsets.Type.systemBars());
        //controller.setSystemBarsBehavior(BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        //window.setNavigationBarColor(Color.BLACK);
        //controller.setSystemBarsAppearance(APPEARANCE_LIGHT_NAVIGATION_BARS,APPEARANCE_LIGHT_NAVIGATION_BARS);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();

        /*windowInsets = (rootView.findViewById(android.R.id.content)).getRootWindowInsets();
        if (windowInsets != null) {
            Log.i(TAG, "StatusBars: " + windowInsets.isVisible(WindowInsets.Type.statusBars()) + " ,navigationBar:" + windowInsets.isVisible(WindowInsets.Type.navigationBars()));
            Log.i(TAG, "StatusBars: " + windowInsets.getInsets(WindowInsets.Type.statusBars()).top + " ,navigationBar:" + windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom);
        } else {
            Log.i(TAG,"windowInsets == null");
        }*/

        //controller.controlWindowInsetsAnimation();
        //rootView.setWindowInsetsAnimationCallback();
        //controller.controlWindowInsetsAnimation(WindowInsets.Type.ime(), 0, new DecelerateInterpolator(), null, new ControlListener());
        // controller.show(WindowInsets.Type.ime());
        //rootView.con
        //rootView.setOnApplyWindowInsetsListener();

        rootView.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            @Override
            public WindowInsets onProgress(WindowInsets insets, List<WindowInsetsAnimation> runningAnimations) {
                Log.i(TAG, "onProgress");
                return insets;
            }
        });

        //rootView.setOnApplyWindowInsetsListener();
        requestWritePermission();
        test();

        //PermissionManager permissionManager = (PermissionManager) getSystemService(Context.PERMISSION_SERVICE);
    }

    private void requestWritePermission() {

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);

        }

    }

    void test() {
   /* String[] projection = new String[]{MediaStore.Images.Media.DATA};
    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
            null, null, null);*/

        Uri mImageUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = MainActivity.this.getContentResolver();

        //只查询jpeg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, null,
                null,
                null, MediaStore.Audio.Media.DATE_MODIFIED);

        if (cursor != null) {
            String path = null;
            Log.i("qiao", "cursor != null, " + cursor.isClosed() + " ," + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (cursor.moveToFirst()) {
                do {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    Log.i("qiao", "cursor:" + Arrays.asList(cursor.getColumnNames()).toString());
                    for (String name : cursor.getColumnNames()) {
                        int index = cursor.getColumnIndex(name);
                        if(!"xmp".contains(name)) {
                            Log.i("qiao", "name: " + name + " , Index: " + index + " ,value: " + (index > -1 ? cursor.getString(index) : "-1"));
                        }else {
                            Log.i("qiao", "name: " + name + " , Index: " + index + " ,value: " + (index > -1 ? cursor.getBlob(index).toString() : "-1"));
                        }
                    }
                    /*if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                        Log.i("qiao", "path:" + path);
                    }*/
                } while (cursor.moveToNext());
            } else {
                Log.i("qiao", "cursor.moveToFirst false");
            }

            cursor.close();
            //int columnIndex1 = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            Log.i("qiao", "cursor != null, " + cursor.isClosed());
        } else {
            Log.i("qiao", "cursor == null");
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                controller.controlWindowInsetsAnimation(WindowInsets.Type.systemBars(), 0, new DecelerateInterpolator(), null, new ControlListener());
                controller.addOnControllableInsetsChangedListener(new WindowInsetsController.OnControllableInsetsChangedListener() {
                    @Override
                    public void onControllableInsetsChanged(WindowInsetsController controller, int typeMask) {
                        Log.i(TAG, "ControlListener onControllableInsetsChanged " + typeMask);
                    }
                });

                windowInsets = rootView.getRootWindowInsets();
                Log.i(TAG, "" + windowInsets.getInsets(WindowInsets.Type.statusBars()).toString());
                Log.i(TAG, "" + windowInsets.getInsets(WindowInsets.Type.navigationBars()).toString());
            }
        }, 3000);
        //controller.addOnControllableInsetsChangedListener();
    }

    public void onClick(View view) {
        while (true){
            if (false)break;
        }
        Log.i(TAG, "onClick-onClick");
    }

    static class ControlListener implements WindowInsetsAnimationControlListener {

        @Override
        public void onReady(WindowInsetsAnimationController controller, int types) {
            Log.i(TAG, "ControlListener onReady");
            controller.setInsetsAndAlpha(Insets.of(0, 0, 0, 80),
                    1f /* alpha */, 0.1f /* fraction progress */);
            //controller.finish(true);
            //controller.
        }

        @Override
        public void onFinished(WindowInsetsAnimationController controller) {
            Log.i(TAG, "ControlListener onFinished");
        }

        @Override
        public void onCancelled(WindowInsetsAnimationController controller) {
            Log.i(TAG, "ControlListener onCancelled");
        }
    }
}