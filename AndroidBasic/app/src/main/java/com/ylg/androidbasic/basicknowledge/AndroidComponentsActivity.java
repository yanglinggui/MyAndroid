package com.ylg.androidbasic.basicknowledge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ylg.androidbasic.R;

import java.util.List;

public class AndroidComponentsActivity extends AppCompatActivity {
    public String title = "Android四大核心组件";
    String TAG = AndroidComponentsActivity.class.getName();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment currentFragment;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "ComponentName= " + name + " ,service= " + service.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "ComponentName= " + name);
        }

    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btna:
                switchFragment(TYPE.ACTIVITY);
                break;
            case R.id.btns:
                switchFragment(TYPE.SERVICE);
                break;
            case R.id.btnb:
                switchFragment(TYPE.BROADCAST);
                break;
            case R.id.btnc:
                switchFragment(TYPE.CONTENTPROVIDER);
                break;
            case R.id.start_service:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.bind_sevvice:
                bindService(new Intent(this, MyService.class), conn, BIND_AUTO_CREATE);
                break;
        }
    }

    enum TYPE {
        ACTIVITY("activity"), SERVICE("service"), BROADCAST("broadcast"), CONTENTPROVIDER("contentprovider");

        private String type;

        private TYPE(String type) {
            this.type = type;
        }

        @NonNull
        @Override
        public String toString() {
            return type;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_components);
        setTitle(title);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.fragment, new ShowActivity());
        //fragmentTransaction.commit();
        switchFragment(TYPE.ACTIVITY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bindService(new Intent(this, MyService.class), conn, BIND_AUTO_CREATE);
    }

    private void switchFragment(TYPE tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            //if (currentFragment.isVisible()) return;
            fragmentTransaction.hide(currentFragment).commit();
            fragmentTransaction = fragmentManager.beginTransaction();
        }
        currentFragment = fragmentManager.findFragmentByTag(tag.toString());
        //if (currentFragment != null && currentFragment.isVisible()) return;
        if (currentFragment == null) {
            switch (tag) {
                case ACTIVITY:
                    currentFragment = new ShowActivity();
                    break;
                case SERVICE:
                    currentFragment = new ShowService();
                    break;
                case BROADCAST:
                    currentFragment = new ShowBroadcast();
                    break;
                case CONTENTPROVIDER:
                    currentFragment = new ShowContentProvider();
                    break;
            }
            fragmentTransaction.add(R.id.fragment, currentFragment, tag.toString()).commit();
        } else {
            fragmentTransaction.show(currentFragment).commit();
        }
    }


    public static class ShowActivity extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activityinfo, container, false);
        }
    }

    public static class ShowService extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.serviceinfo, container, false);
        }
    }

    public static class ShowBroadcast extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.broadcastinfo, container, false);
        }
    }

    public static class ShowContentProvider extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.contentprovider, container, false);
        }
    }

    public static class MyService extends Service {
        String TAG = MyService.class.getName();

        @Override
        public void onCreate() {
            super.onCreate();
            Log.i(TAG, "MyService onCreate");
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i(TAG, "MyService onStartCommand");
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "MyService onDestroy");
            super.onDestroy();
        }

        @Override
        public boolean onUnbind(Intent intent) {
            Log.i(TAG, "MyService onUnbind");
            return super.onUnbind(intent);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            Log.i(TAG, "MyService onBind");
            return null;
        }
    }
}