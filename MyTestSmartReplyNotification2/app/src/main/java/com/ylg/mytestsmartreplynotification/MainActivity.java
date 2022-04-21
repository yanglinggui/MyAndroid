package com.ylg.mytestsmartreplynotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String KEY_REPLY = "key_reply";
    String KEY_REPLY_HISTORY = "key_reply_history";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotifyManager;
    private Notification.Builder mNBuilder;
    int count = 0;
    private static final String SYSTEM_NAVIGTION_ACTION = "android.settings.ACTION_POWER_MENU_SETTINGS";
    private Intent intent;
    private static final String action1 = "taddAction1";
    private static final String action2 = "taddAction2";
    private static final String action3 = "taddAction3";
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //createInlineNotification();
        //sendNotification1();
        mImageView = findViewById(R.id.tv);
        Rect previewBounds = new Rect();
       // mImageView.getBoundsOnScreen(previewBounds);
        createInlineNotification();
        init();
    }




    private  NetWorkChangeReceiver netWorkChangeReceiver;
    private IntentFilter intentFilter;

    private void init() {

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.jems.btn.home");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        //注册广播
        registerReceiver(netWorkChangeReceiver,intentFilter);
    }




    private class NetWorkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("qiao","intent: " + intent.getAction());
        }
    }




    private void createInlineNotification() {

        //Create notification builder
        Notification.Builder builder = new Notification.Builder(this, "sendNotification1")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("www.test.com")
                .setContentText("www.test.com")
                .setAllowSystemGeneratedContextualActions(true);
        builder.setStyle(new Notification.BigTextStyle().setBigContentTitle("www.test.com").setSummaryText("www.test.com"));

        String replyLabel = "Enter your reply here";

        //Initialise RemoteInput
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
                .setLabel(replyLabel)
                //.setChoices(new CharSequence[] {"test","test","test","test","test","test","test","test"})
                .build();


        int randomRequestCode = new Random().nextInt(54325);

        //PendingIntent that restarts the current activity instance.
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Set a unique request code for this pending intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, randomRequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        //Notification Action with RemoteInput instance added.
        Notification.Action replyAction = new Notification.Action.Builder(
                android.R.drawable.sym_action_chat, "REPLY", resultPendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                //.setContextual(true)
                .build();

        //Notification.Action instance added to Notification Builder.
        builder.addAction(replyAction);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("notificationId", NOTIFICATION_ID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dismissIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "DISMISS", dismissIntent);
        //builder.setSound(null);

        //Create Notification.
        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("sendNotification1",
                "channel", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify("sendNotification1", NOTIFICATION_ID,
                builder.build());

    }


    public void sendNotification1() {
        NotificationChannel channel = new NotificationChannel("sendNotification1",
                "channel", NotificationManager.IMPORTANCE_HIGH);
         mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.createNotificationChannel(channel);

        /*PendingIntent addAction1Intent = PendingIntent.getBroadcast(this, 0,
                new Intent().setAction(action1), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent addAction2Intent = PendingIntent.getBroadcast(this, 0,
                new Intent().setAction(action2), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent addAction3Intent = PendingIntent.getBroadcast(this, 0,
                new Intent().setAction(action3), PendingIntent.FLAG_UPDATE_CURRENT);*/
        Notification.Builder publicBuilder = new Notification.Builder(this);
        publicBuilder
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor(getColor(R.color.design_default_color_primary_dark))
                .setContentTitle("publicBuilder")
                .setUsesChronometer(true)
                .setWhen(SystemClock.currentThreadTimeMillis());
        publicBuilder.setColorized(true);
        final Bundle extras = new Bundle();
        extras.putString("android.substName", "appName");
        mNBuilder = new Notification.Builder(this, "sendNotification1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                //.set
                .setLargeIcon(Icon.createWithResource(this, R.drawable.ic_launcher_background))
                .setContentTitle("title test")
                .setContentText("content test")
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
                /*.setActions(new Notification.Action.Builder(null, "addAction1", addAction1Intent).setAllowGeneratedReplies(false).build(),
                        new Notification.Action.Builder(null, "addAction2", addAction2Intent).setAllowGeneratedReplies(false).build(),
                        new Notification.Action.Builder(null, "addAction3", addAction3Intent).setAllowGeneratedReplies(false).build())*/
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

        //try {
        //notification.bigContentView.setViewVisibility(
        //        android.R.id.actions_container, View.VISIBLE);
        // } catch (Exception e) {
        //     Log.i("qiao","Error: " + e.getMessage());
        // }
        //notification.
        //notification.color = Color.BLUE;


        mNotifyManager.notify("sendNotification1", 1, notification);
        //mNBuilder.ac
    }

}