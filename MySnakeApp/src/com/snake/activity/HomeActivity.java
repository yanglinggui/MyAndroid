package com.snake.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mysnakeapp.R;
import com.snake.bean.scoreBean;
import com.snake.constant.setting;
import com.snake.db.scoreManger;
import com.snake.view.SnakeView;

public class HomeActivity extends Activity implements OnClickListener {

	private long oldTime = 0, newTime;// 限制按钮点击间隔时间

	private Button mButtonUp, mButtonRight, mButtonDown, mButtonLeft;// 方向按钮

	private Button mButtonNewGame, mButtonAgainGame, mButtonSettingGame,
			mButtonHelpGame, mButtonHeroGame, mButtonExitGame;// setting按钮

	private CheckBox mCheckBoxStop;// 暂停游戏

	private ScrollView mScrollViewSetting;// 控制setting按钮的显示

	private SnakeView mSnakeView;

	private TextView mTextViewScore, mTextViewHighScore;// 分数

	private SharedPreferences mPreferences;// 存储setting信息

	private scoreManger mScoreManger;// 存储分数信息

	private EditText mEditTextInputName;// 输入英雄榜姓名

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

				String reason = intent.getStringExtra("reason");
				if (TextUtils.equals(reason, "homekey")) {
					// 表示按了home键,程序到了后台
					stopService(new Intent("com.snake.service.MusicService"));// 关闭音乐服务
				}
				// else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
				// //表示长按home键,显示最近使用的程序列表
				// }

			}

			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				stopService(new Intent("com.snake.service.MusicService"));// 关闭音乐服务
			}

			if (((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE))
					.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
				stopService(new Intent("com.snake.service.MusicService"));// 关闭音乐服务
			} else if (((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE))
					.getCallState() == TelephonyManager.CALL_STATE_IDLE) {// TelephonyManager.CALL_STATE_IDLE
				Intent intentMusic = new Intent(
						"com.snake.service.MusicService");// 播放背景音乐
				intentMusic.addFlags(2);
				startService(intentMusic);
			}

			if (intent.getAction().equals("UpdateScore")) {// 更新分数
				mTextViewScore.setText("" + mSnakeView.getSnakeScore());
			}

			if (intent.getAction().equals("SnakeDeath")) {// 游戏结束处理
				gameOver();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.snake);
		// // 方法1 Android获得屏幕的宽和高
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// int screenWidth = screenWidth = display.getWidth();
		// int screenHeight = screenHeight = display.getHeight();
		// Log.i("TAG", "screenWidth=" + screenWidth + "=====+screenHeight="
		// + screenHeight);
		//
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// float width = dm.widthPixels * dm.density;
		// float height = dm.heightPixels * dm.density;
		// Log.i("TAG", "dm.widthPixels=" + dm.widthPixels
		// + "=====+dm.heightPixels=" + dm.heightPixels);
		// Log.i("TAG", "width=" + width + "=====+height=" + height);
		// Log.i("TAG", "dm.density=" + dm.density);

		init();
	}

	private void init() {// 初始化数据
		// TODO Auto-generated method stub

		/* ========获取屏幕密度======== */
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		setting.DENSITY = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		Log.i("TAG", "" + metric.density);
		/* ========注册广播======== */
		MyRegisterReceiver();

		/* ========控制蛇的方向的按键======== */
		mButtonUp = (Button) findViewById(R.id.up);
		mButtonUp.setOnClickListener(this);

		mButtonRight = (Button) findViewById(R.id.right);
		mButtonRight.setOnClickListener(this);

		mButtonDown = (Button) findViewById(R.id.down);
		mButtonDown.setOnClickListener(this);

		mButtonLeft = (Button) findViewById(R.id.left);
		mButtonLeft.setOnClickListener(this);

		mCheckBoxStop = (CheckBox) findViewById(R.id.stop);
		mCheckBoxStop.setOnClickListener(this);
		/* ========setting按钮======== */
		mScrollViewSetting = (ScrollView) findViewById(R.id.setting);

		mButtonNewGame = (Button) findViewById(R.id.new_game);
		mButtonNewGame.setOnClickListener(this);

		mButtonAgainGame = (Button) findViewById(R.id.again_game);
		mButtonAgainGame.setOnClickListener(this);

		mButtonSettingGame = (Button) findViewById(R.id.setting_game);
		mButtonSettingGame.setOnClickListener(this);

		mButtonHelpGame = (Button) findViewById(R.id.Description_game);
		mButtonHelpGame.setOnClickListener(this);

		mButtonHeroGame = (Button) findViewById(R.id.score_game);
		mButtonHeroGame.setOnClickListener(this);

		mButtonExitGame = (Button) findViewById(R.id.exit_game);
		mButtonExitGame.setOnClickListener(this);
		/* =========snake空间======== */
		mSnakeView = (SnakeView) findViewById(R.id.snake);

		/* =========显示分数======== */
		mTextViewScore = (TextView) findViewById(R.id.Score);
		mTextViewHighScore = (TextView) findViewById(R.id.HighScore);

		/* =========读取setting信息======== */
		mPreferences = getSharedPreferences("setting", MODE_PRIVATE);
		setting.MODEL = mPreferences.getInt("setting.MODEL", setting.EASE);
		setting.SNAKE_MOVEMENT_SPEED = mPreferences.getInt(
				"setting.SNAKE_MOVEMENT_SPEED", 100);
		setting.MUSIC_ON_OFF = mPreferences.getBoolean("setting.MUSIC_ON_OFF",
				false);
		setting.WALL_YES_NO = mPreferences.getBoolean("setting.WALL_YES_NO",
				false);
		mScoreManger = new scoreManger(HomeActivity.this);
		/* =========初始化游戏状态======== */
		mButtonAgainGame.setEnabled(setting.SNAKE_IS_LIVING);

		Intent intent = new Intent("com.snake.service.MusicService");// 播放背景音乐
		intent.addFlags(2);
		startService(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (isTimeLimit()) {
			switch (v.getId()) {
			/* ======控制蛇的移动方向===== */
			case R.id.up:
				mSnakeView.setSnakeDirection(mSnakeView.UP);
				break;
			case R.id.right:
				mSnakeView.setSnakeDirection(mSnakeView.RIGHT);
				break;
			case R.id.down:
				mSnakeView.setSnakeDirection(mSnakeView.DOWN);
				break;
			case R.id.left:
				mSnakeView.setSnakeDirection(mSnakeView.LEFT);
				break;
			/* ======setting按钮监听===== */
			case R.id.new_game:
				mTextViewHighScore.setText(""
						+ (mScoreManger.query().size() > 0 ? mScoreManger
								.query().get(0).getScore() : 0));
				mSnakeView.newGame();
				mCheckBoxStop.setChecked(mSnakeView.getSnakeIsRunning());
				mScrollViewSetting.setVisibility(View.GONE);
				mButtonAgainGame.setEnabled(setting.SNAKE_IS_LIVING);
				break;
			case R.id.again_game:
				mTextViewHighScore.setText(""
						+ (mScoreManger.query().size() > 0 ? mScoreManger
								.query().get(0).getScore() : 0));
				mSnakeView.againGame();
				mCheckBoxStop.setChecked(mSnakeView.getSnakeIsRunning());
				mScrollViewSetting.setVisibility(View.GONE);
				break;
			case R.id.stop:
				mTextViewHighScore.setText(""
						+ (mScoreManger.query().size() > 0 ? mScoreManger
								.query().get(0).getScore() : 0));
				if (mCheckBoxStop.isChecked()) {
					mSnakeView.againGame();
					mScrollViewSetting.setVisibility(View.GONE);
				} else {
					mSnakeView.stopGame();
					mScrollViewSetting.setVisibility(View.VISIBLE);
				}
				mButtonAgainGame.setEnabled(setting.SNAKE_IS_LIVING);
				break;
			case R.id.setting_game:
				startActivity(new Intent(HomeActivity.this,
						SettingActivity.class));
				break;
			case R.id.Description_game:
				startActivity(new Intent(HomeActivity.this, HelpActivity.class));
				break;
			case R.id.score_game:
				startActivity(new Intent(HomeActivity.this, HeroActivity.class));
				break;
			case R.id.exit_game:
				finish();
				break;
			default:
			}
		}
	}

	@Override
	protected void onPause() {// 暂停游戏
		// TODO Auto-generated method stub
		super.onPause();

		mSnakeView.stopGame();
		mCheckBoxStop.setChecked(mSnakeView.getSnakeIsRunning());
		mScrollViewSetting.setVisibility(View.VISIBLE);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/* ========存储数据库信息====== */
		if (setting.SNAKE_IS_LIVING) {
			mSnakeView.saveGame();
		}
		unregisterReceiver(mBroadcastReceiver);// 关闭广播及服务
		Intent intent = new Intent("com.snake.service.MusicService");// 关闭服务
		stopService(intent);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		Intent intent = new Intent("com.snake.service.MusicService");// 播放背景音乐
		intent.addFlags(2);
		startService(intent);

	}

	/* ========自定义函数====== */

	private boolean isTimeLimit() {

		oldTime = newTime;
		newTime = System.currentTimeMillis();
		if (oldTime != 0 && (newTime - oldTime < 200))
			return false;

		return true;

	}

	private void MyRegisterReceiver() {// 注册广播

		IntentFilter filter = new IntentFilter();
		filter.addAction("UpdateScore");
		filter.addAction("SnakeDeath");
		filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);// 来电监听
		filter.addAction(Intent.ACTION_SCREEN_OFF);// 灭屏
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);// 主页键
		HomeActivity.this.registerReceiver(mBroadcastReceiver, filter);
	}

	private void gameOver() {// 游戏结束的处理
		/* ========最终的分处理====== */
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(
				HomeActivity.this);
		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog, null);
		mBuilder.setTitle(R.string.title);
		int rank = mScoreManger.queryRank(mSnakeView.getSnakeScore()) + 1;
		mBuilder.setMessage("恭喜进入英雄榜第" + rank + "名！\n请输入姓名:");
		mBuilder.setView(view);
		mEditTextInputName = (EditText) view.findViewById(R.id.input_name);
		mBuilder.setNegativeButton(R.string.cancle, null);
		mBuilder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						scoreBean mScoreBean = new scoreBean();
						String name = mEditTextInputName.getText().toString();
						mScoreBean.setName(name.equals("") ? "佚名" : name);
						mScoreBean.setScore(mSnakeView.getSnakeScore());
						mScoreManger.insert(mScoreBean);
					}
				});
		// mBuilder.get
		mBuilder.create();// .show();
		Dialog noticeDialog = mBuilder.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
		/* ========游戏结束处理====== */
		mButtonAgainGame.setEnabled(setting.SNAKE_IS_LIVING);
		mScrollViewSetting.setVisibility(View.VISIBLE);
		mCheckBoxStop.setChecked(mSnakeView.getSnakeIsRunning());
	}
}
