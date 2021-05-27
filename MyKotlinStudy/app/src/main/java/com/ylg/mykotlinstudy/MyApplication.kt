package com.ylg.mykotlinstudy

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log


class MyApplication : Application() {

    val TAG = MyApplication::class.java.name
    val activitys1: List<String> = ArrayList<String>()
    var activitys = ArrayList<String>()

    override fun onCreate() {
        super.onCreate()
        try {
            //val packageManager = packageManager
            val packageInfo = packageManager.getPackageInfo(
                packageName, PackageManager.GET_ACTIVITIES
            )
            for (activity in packageInfo.activities) {
                Log.i(TAG, "name: " + activity.name)
                if (activity.name == MainActivity::class.java.name) continue
                activitys.add(activity.name);
            }
        } catch (e: Exception) {
            Log.i(TAG, "get all activity error: " + e.message)
        }
    }

}