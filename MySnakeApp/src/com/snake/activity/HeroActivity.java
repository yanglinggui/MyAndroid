package com.snake.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.example.mysnakeapp.R;
import com.snake.adapter.HeroAdapter;
import com.snake.bean.scoreBean;
import com.snake.db.scoreManger;

public class HeroActivity extends Activity implements OnClickListener {

	private Button mButtonBack, mButtonAll, mButtonDelete;//

	private ListView mListView;

	private List<scoreBean> scoreBeanList;

	private scoreManger mScoreManger;

	private HeroAdapter mHeroAdapter;

	private boolean isAllSelect = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hero);

		init();
	}

	private void init() {// 初始化数据
		// TODO Auto-generated method stub
		mButtonBack = (Button) findViewById(R.id.back);
		mButtonBack.setOnClickListener(this);

		mButtonAll = (Button) findViewById(R.id.all);
		mButtonAll.setOnClickListener(this);

		mButtonDelete = (Button) findViewById(R.id.delete);
		mButtonDelete.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.heroInformation);
		// 读取数据库信息
		mScoreManger = new scoreManger(HeroActivity.this);
		scoreBeanList = mScoreManger.query();
		mHeroAdapter = new HeroAdapter(getApplicationContext(), scoreBeanList);
		mListView.setAdapter(mHeroAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.all:
			if (scoreBeanList.size() < 1) {
				break;
			}
			if (isAllSelect) {
				mButtonAll.setText(R.string.cancle);
			} else {
				mButtonAll.setText(R.string.all);
			}
			for (scoreBean mScoreBean : scoreBeanList) {
				mScoreBean.setCheck(isAllSelect);
			}
			isAllSelect = !isAllSelect;
			break;
		case R.id.delete:
			List<scoreBean> mList = new ArrayList<scoreBean>();
			for (scoreBean item : scoreBeanList) {
				if (item.isCheck()) {
					mList.add(item);
				}
			}
			scoreBeanList.removeAll(mList);
			int rank = 0;
			int score = -1;
			for (scoreBean scoreBean : scoreBeanList) {
				if (score != scoreBean.getScore()) {
					score = scoreBean.getScore();
					rank++;
				}
				scoreBean.setRank(rank);
			}

			mScoreManger.delete(mList);
			mButtonAll.setText(R.string.all);
			break;
		default:
		}

		mHeroAdapter.notifyDataSetChanged();
	}
}
