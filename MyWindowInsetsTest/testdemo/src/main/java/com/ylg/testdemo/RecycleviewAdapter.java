package com.ylg.testdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.ViewHoloder> {

    //我的消息
    private final int ITEM_TYPE_MESSAGE_SELF = 0;
    //对方的消息
    private final int ITEM_TYPE_MESSAGE_OTHER = 1;
    //消息条数
    private final int NUMBER_MESSAGES = 20;

    @NonNull
    @Override
    public ViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //获取布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //根据类型返回不同的布局
        View view;
        if (viewType == ITEM_TYPE_MESSAGE_OTHER) {
            view = layoutInflater.inflate(R.layout.message_bubble, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.message_bubble_self, parent, false);
        }
        return new ViewHoloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoloder holder, int position) {
        //不需要
    }

    @Override
    public int getItemCount() {
        //给它20条
        return NUMBER_MESSAGES;
    }

    @Override
    public int getItemViewType(int position) {
        //间隔的返回消息类型
        if (position % 2 == 0) {
            return ITEM_TYPE_MESSAGE_OTHER;
        } else {
            return ITEM_TYPE_MESSAGE_SELF;
        }
    }

    static class ViewHoloder extends RecyclerView.ViewHolder {

        ViewHoloder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
