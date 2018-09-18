package com.snake.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.example.mysnakeapp.R;
import com.snake.bean.scoreBean;

public class HeroAdapter extends BaseAdapter {

	private Context mContext;

	private List<scoreBean> mList;

	public HeroAdapter(Context mContext, List<scoreBean> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Holder mHolder;
		if (null == convertView) {
			mHolder = new Holder();
			convertView = View.inflate(mContext, R.layout.hero_item, null);
			mHolder.mTextViewRank = (TextView) convertView
					.findViewById(R.id.rank);
			mHolder.mTextViewName = (TextView) convertView
					.findViewById(R.id.name);
			mHolder.mTextViewScore = (TextView) convertView
					.findViewById(R.id.score);
			mHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}

		mHolder.mTextViewRank.setText("" + mList.get(position).getRank());
		mHolder.mTextViewName.setText(mList.get(position).getName());
		mHolder.mTextViewScore.setText("" + mList.get(position).getScore());
		mHolder.mCheckBox.setChecked(mList.get(position).isCheck());
		new MyOnCheckedChangeListener(mHolder.mCheckBox, position);
		return convertView;
	}

	/* ====¸¨Öúº¯Êý===== */

	class Holder {
		public TextView mTextViewRank, mTextViewName, mTextViewScore;
		public CheckBox mCheckBox;
	}

	class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		int position;

		public MyOnCheckedChangeListener(CheckBox mCheckBox, int position) {
			this.position = position;
			mCheckBox.setOnCheckedChangeListener(this);
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			mList.get(position).setCheck(isChecked);
		}

	}
}
