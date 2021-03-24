package com.ylg.testsubcomponent.Dependencies;

import android.app.Activity;
import android.os.Bundle;

import com.ylg.testsubcomponent.bean.NewStudent;
import com.ylg.testsubcomponent.bean.Teacher;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class DependenciesAcvitity extends Activity {

    @Inject
    NewStudent student;

    @Inject
    Teacher teacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDStudentComponent.builder().dTeacherComponent(DaggerDTeacherComponent.create()).dStudentModule(new DStudentModule()).build().inject(this);

        android.util.Log.i("qiao","DependenciesAcvitity student " + student.toString() + " ,teacher " + teacher.toString());
    }
}
