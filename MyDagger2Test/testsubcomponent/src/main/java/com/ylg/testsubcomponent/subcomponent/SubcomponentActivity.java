package com.ylg.testsubcomponent.subcomponent;

import android.app.Activity;
import android.os.Bundle;

import com.ylg.testsubcomponent.bean.NewStudent;
import com.ylg.testsubcomponent.bean.Teacher;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;

public class SubcomponentActivity extends Activity {


    @Inject
    NewStudent student;

    @Inject
    Teacher teacher;

    @Inject
    NewStudent student1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSubTecherComponent.create().buildStudentComponent().build().inject(this);
        android.util.Log.i("qiao","SubcomponentActivity student " + student.toString() +
                " ,teacher " + teacher.toString() + " ,student1 = " + student1.toString());
    }
}
