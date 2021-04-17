package com.ylg.androidbasic;

import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    String TAG = MyApplication.class.getName();
    List activitys = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();
        /*try {
            //Class clazz = getClassLoader().loadClass("com.ylg.androidbasic.TestActivity");
            Class clazz = Class.forName("com.ylg.androidbasic.TestActivity");
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (Exception e) {
            Log.i(TAG, "getClassLoader().loadClass() error: " + e.getMessage());
        }*/

        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activity : packageInfo.activities) {
                Log.i(TAG, "name: " + activity.name);
                if(activity.name.equals(MainActivity.class.getName())) continue;
                activitys.add(activity.name);
            }
        } catch (Exception e) {
            Log.i(TAG, "get all activity error: " + e.getMessage());
        }
    }

    public List getActivitys() {
        return activitys;
    }
}
