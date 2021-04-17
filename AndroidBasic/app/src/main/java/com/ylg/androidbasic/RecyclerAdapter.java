package com.ylg.androidbasic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    String TAG = RecyclerAdapter.class.getName();

    private LayoutInflater inflater;
    private List<String> activitys;
    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        activitys = ((MyApplication) context.getApplicationContext()).getActivitys();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler_layout, parent, false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            //Class clazz = getClassLoader().loadClass("com.ylg.androidbasic.TestActivity");
            Class clazz = Class.forName(activitys.get(position));
            //Log.i(TAG, "Title = " + clazz.getDeclaredField("title1").get(clazz.newInstance()));
            holder.btn.setText(clazz.getDeclaredField("title").get(clazz.newInstance()).toString());
        } catch (Exception e) {
            Log.i(TAG, "RecyclerAdapter onBindViewHolder {position=" + position + ", "
                    + activitys.get(position) + "} error: " + e.getMessage());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Class clazz = getClassLoader().loadClass("com.ylg.androidbasic.TestActivity");
                    Class clazz = Class.forName(activitys.get(position));
                    //Activity clazz1 =  (Activity)clazz.newInstance();
                    //Log.i(TAG, "Title = " + clazz.getDeclaredField("title1").get(clazz.newInstance()));
                    Intent intent = new Intent(context, clazz);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Log.i(TAG, "RecyclerAdapter startActivity {position=" + position + ", "
                            + activitys.get(position) + "} error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activitys.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = (Button) itemView;
        }
    }
}
