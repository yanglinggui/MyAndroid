package com.ylg.androidnotificationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.RemoteViews;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotifyManager;
    private Notification.Builder mNBuilder;
    int count = 0;

    private static final String action1 = "taddAction1";
    private static final String action2 = "taddAction2";
    private static final String action3 = "taddAction3";

    private BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getAction();
            //Log.i("qiao", "intent = " + str);
            mNotifyManager.cancelAll();
            switch (str) {
                case action1:
                    Log.i("qiao", "intent = " + str);
                    startActivity(new Intent()
                            .setPackage("com.android.settings")
                            .setAction(Settings.ACTION_INTERNAL_STORAGE_SETTINGS)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case action2:
                    Log.i("qiao", "intent = " + str);
                    break;
                case action3:
                    Log.i("qiao", "intent = " + str);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action1);
        intentFilter.addAction(action2);
        intentFilter.addAction(action3);
        this.registerReceiver(myBroadcast, intentFilter);
        //this.name
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show:
                sendNotification1();
                break;
            case R.id.clear:
                mNotifyManager.cancelAll();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(myBroadcast);
    }

    public void sendNotification1() {
        NotificationChannel channel = new NotificationChannel("sendNotification1",
                "channel", NotificationManager.IMPORTANCE_HIGH);
        mNotifyManager.createNotificationChannel(channel);

        PendingIntent addAction1Intent = PendingIntent.getBroadcast(this, 0,
                new Intent().setAction(action1), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent addAction2Intent = PendingIntent.getBroadcast(this, 0,
                new Intent().setAction(action2), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent addAction3Intent = PendingIntent.getBroadcast(this, 0,
                new Intent().setAction(action3), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder publicBuilder = new Notification.Builder(this);
        publicBuilder
                .setSmallIcon(R.drawable.ic_large_icon)
                .setColor(getColor(R.color.design_default_color_primary_dark))
                .setContentTitle("publicBuilder")
                .setUsesChronometer(true)
                .setWhen(SystemClock.currentThreadTimeMillis());
        publicBuilder.setColorized(true);
        final Bundle extras = new Bundle();
        extras.putString("android.substName", "appName");
        mNBuilder = new Notification.Builder(this, "sendNotification1")
                .setSmallIcon(R.drawable.ic_small_icon)
                //.set
                .setLargeIcon(Icon.createWithResource(this, R.drawable.test))
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content) + (++count))
                //.setAutoCancel(true)//点击通知后自动清除
                .setContentIntent(PendingIntent.getActivity(this, 0,
                        new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                /*.addAction(new Notification.Action.Builder(
                        null,
                        "addAction1",
                        addAction1Intent)
                        .build())
                .addAction(new Notification.Action.Builder(
                        null,
                        "addAction2",
                        addAction2Intent)
                        .build())
                .addAction(new Notification.Action.Builder(
                        null,
                        "addAction3",
                        addAction3Intent)
                        .build())*/
                .setActions(new Notification.Action.Builder(null, "addAction1", addAction1Intent).setAllowGeneratedReplies(false).build(),
                        new Notification.Action.Builder(null, "addAction2", addAction2Intent).setAllowGeneratedReplies(false).build(),
                        new Notification.Action.Builder(null, "addAction3", addAction3Intent).setAllowGeneratedReplies(false).build())
                .setGroup("mysetGroup")
                .setColor(Color.BLUE)
                .addExtras(extras)
        .setAllowSystemGeneratedContextualActions(false)

        ;

               /* .setCategory(Notification.CATEGORY_CALL)
        .setPriority(Notification.PRIORITY_MAX)
.setAllowSystemGeneratedContextualActions(true)
        .setPublicVersion(publicBuilder.build())*/
        mNBuilder.setStyle(new Notification.BigTextStyle().setBigContentTitle("setBigContentTitle").setSummaryText("setSummaryText"));
        //mNBuilder.ac
        Notification notification = mNBuilder.build();
        //mNBuilder.
        //notification.getA
        notification.priority = Notification.PRIORITY_MAX;
        //notification.
        //notification.flags |= Notification.FLAG_INSISTENT;
        // notification.headsUpContentView.setBoolean(0x010203ab, "setExpanded", true);
        Log.i("qiao", "headsUpContentView= " + (notification.headsUpContentView == null)
                + " ,contentView= " + (notification.contentView == null)
                + " ,cbigContentView= " + (notification.bigContentView == null)
                + " ,ctickerView= " + (notification.tickerView == null));
        Log.i("qiao", "get headsUpContentView= " + (mNBuilder.getNotification().headsUpContentView == null)
                + " ,ccontentView= " + (mNBuilder.getNotification().contentView == null)
                + " ,cbigContentView= " + (mNBuilder.getNotification().bigContentView == null)
                + " ,ctickerView= " + (mNBuilder.getNotification().tickerView == null));
        //try {
        //notification.bigContentView.setViewVisibility(
        //        android.R.id.actions_container, View.VISIBLE);
        // } catch (Exception e) {
        //     Log.i("qiao","Error: " + e.getMessage());
        // }
        //notification.
        //notification.color = Color.BLUE;

        for (StatusBarNotification mStatusBarNotification : mNotifyManager.getActiveNotifications()) {
            Log.i("qiao", "mStatusBarNotification befor= " + mStatusBarNotification.toString());
        }
        mNotifyManager.notify("sendNotification1", 1, notification);
        SystemClock.sleep(1000);
        for (StatusBarNotification mStatusBarNotification : mNotifyManager.getActiveNotifications()) {
            Log.i("qiao", "mStatusBarNotification after= " + mStatusBarNotification.toString());
        }
        //mNBuilder.ac
    }

}