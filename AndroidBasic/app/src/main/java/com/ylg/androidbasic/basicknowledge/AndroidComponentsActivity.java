package com.ylg.androidbasic.basicknowledge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ylg.androidbasic.R;

public class AndroidComponentsActivity extends AppCompatActivity {
    public String title = "Android四大核心组件";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment currentFragment;

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

    private void switchFragment(TYPE tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment).commit();
            fragmentTransaction = fragmentManager.beginTransaction();
        }
        currentFragment = fragmentManager.findFragmentByTag(tag.toString());
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
            return inflater.inflate(R.layout.activityinfo, container, false);
        }
    }

    public static class ShowBroadcast extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activityinfo, container, false);
        }
    }

    public static class ShowContentProvider extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_test, container, false);
        }
    }
}