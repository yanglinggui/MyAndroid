package com.snake.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.mysnakeapp.R;
import com.snake.constant.setting;

public class SettingActivity extends Activity implements OnClickListener {

	private Button mButtonBackOk, mButtonBack;

	private RadioGroup mRadioGroupLevel, mRadioGroupMusic, mRadioGroupWall;

	private SharedPreferences mSharedPreferencesSetting;

	private Spinner mSpinnerSelectSpeed;

	private ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_setting);

		init();
	}

	private void init() {
		// TODO Auto-generated method stub

		mSharedPreferencesSetting = getSharedPreferences("setting",
				MODE_PRIVATE);

		mButtonBackOk = (Button) findViewById(R.id.backOk);
		mButtonBackOk.setOnClickListener(this);

		mButtonBack = (Button) findViewById(R.id.back);
		mButtonBack.setOnClickListener(this);

		mRadioGroupLevel = (RadioGroup) findViewById(R.id.level);
		mSpinnerSelectSpeed = (Spinner) findViewById(R.id.selectSpeed);
		mRadioGroupMusic = (RadioGroup) findViewById(R.id.music);
		mRadioGroupWall = (RadioGroup) findViewById(R.id.wall);

		// mAdapter = new ArrayAdapter<String>(getApplicationContext(),
		// android.R.layout.simple_list_item_1, getResources()
		// .getStringArray(R.array.snakeSpeed));
		//
		// mSpinnerSelectSpeed.setAdapter(mAdapter);
		mAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.spinner_item, getResources().getStringArray(
						R.array.snakeSpeed));

		mSpinnerSelectSpeed.setAdapter(mAdapter);

		switch (setting.MODEL) {
		case setting.EASE:
			mRadioGroupLevel.check(R.id.easy);
			break;
		case setting.MEDIUM:
			mRadioGroupLevel.check(R.id.medium);
			break;
		case setting.HARD:
			mRadioGroupLevel.check(R.id.hard);
			break;
		default:
		}

		mSpinnerSelectSpeed
				.setSelection(9 - setting.SNAKE_MOVEMENT_SPEED / 100);

		mRadioGroupMusic.check(setting.MUSIC_ON_OFF == true ? R.id.on
				: R.id.off);
		mRadioGroupWall.check(setting.WALL_YES_NO == true ? R.id.yes : R.id.no);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.backOk) {
			switch (mRadioGroupLevel.getCheckedRadioButtonId()) {// 游戏难度
			case R.id.easy:
				setting.MODEL = setting.EASE;
				break;
			case R.id.medium:
				setting.MODEL = setting.MEDIUM;
				break;
			case R.id.hard:
				setting.MODEL = setting.HARD;
				break;
			default:
			}

			setting.SNAKE_MOVEMENT_SPEED = 1000 - Integer
					.parseInt(mSpinnerSelectSpeed.getSelectedItem().toString());// 速度

			switch (mRadioGroupMusic.getCheckedRadioButtonId()) {// 音乐
			case R.id.on:
				setting.MUSIC_ON_OFF = true;
				break;
			case R.id.off:
				setting.MUSIC_ON_OFF = false;
				break;
			default:
			}

			switch (mRadioGroupWall.getCheckedRadioButtonId()) {// 墙壁
			case R.id.yes:
				setting.WALL_YES_NO = true;
				break;
			case R.id.no:
				setting.WALL_YES_NO = false;
				break;
			default:
			}

			// 保存设置信息
			Editor mEditor = mSharedPreferencesSetting.edit();
			mEditor.putInt("setting.MODEL", setting.MODEL);
			mEditor.putInt("setting.SNAKE_MOVEMENT_SPEED",
					setting.SNAKE_MOVEMENT_SPEED);
			mEditor.putBoolean("setting.MUSIC_ON_OFF", setting.MUSIC_ON_OFF);
			mEditor.putBoolean("setting.WALL_YES_NO", setting.WALL_YES_NO);
			mEditor.commit();

			Intent intent = new Intent("com.snake.service.MusicService");
			intent.addFlags(2);
			startService(intent);

		}
		finish();
	}
}
