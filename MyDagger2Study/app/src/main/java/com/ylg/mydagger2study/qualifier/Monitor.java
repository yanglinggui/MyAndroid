package com.ylg.mydagger2study.qualifier;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class Monitor {

    private Context context;
    private TextView textView;

    public Monitor(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    //public void show(TextView textView, Computer computer) {
    public void show(Computer computer) {
        StringBuilder builder = new StringBuilder();
        computer.execute(builder);
        textView.setText(builder.toString());
    }

    //public void startRefresh(TextView textView, Date date) {
    public void startRefresh(Date date) {
        StringBuilder builder = new StringBuilder(textView.getText());
        builder.append(date.toString() + "\n");
        textView.setText(builder.toString());
    }

    //public void toastInfo(Context context){
    public void toastInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append("Monitor: " + this.hashCode());
        builder.append("Context: ").append(context.hashCode()).append("\n");
        builder.append("TextView: ").append(textView.hashCode());
        Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show();
    }

    public void toastAppname(String appname){

        Toast.makeText(context, appname, Toast.LENGTH_SHORT).show();
    }
}
