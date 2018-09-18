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

	private long oldTime = 0, newTime;// ���ư�ť������ʱ��

	private Button mButtonUp, mButtonRight, mButtonDown, mButtonLeft;// ����ť

	private Button mButtonNewGame, mButtonAgainGame, mButtonSettingGame,
			mButtonHelpGame, mButtonHeroGame, mButtonExitGame;// setting��ť

	private CheckBox mCheckBoxStop;// ��ͣ��Ϸ

	private ScrollView mScrollViewSetting;// ����setting��ť����ʾ

	private SnakeView mSnakeView;

	private TextView mTextViewScore, mTextViewHighScore;// ����

	private SharedPreferences mPreferences;// �洢setting��Ϣ

	private scoreManger mScoreManger;// �洢������Ϣ

	private EditText mEditTextInputName;// ����Ӣ�۰�����

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

				String reason = intent.getStringExtra("reason");
				if (TextUtils.equals(reason, "homekey")) {
					// ��ʾ����home��,�����˺�̨
					stopService(new Intent("com.snake.service.MusicService"));// �ر����ַ���
				}
				// else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
				// //��ʾ����home��,��ʾ���ʹ�õĳ����б�
				// }

			}

			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				stopService(new Intent("com.snake.service.MusicService"));// �ر����ַ���
			}

			if (((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE))
					.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
				stopService(new Intent("com.snake.service.MusicService"));// �ر����ַ���
			} else if (((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE))
					.getCallState() == TelephonyManager.CALL_STATE_IDLE) {// TelephonyManager.CALL_STATE_IDLE
				Intent intentMusic = new Intent(
						"com.snake.service.MusicService");// ���ű�������
				intentMusic.addFlags(2);
				startService(intentMusic);
			}

			if (intent.getAction().equals("UpdateScore")) {// ���·���
				mTextViewScore.setText("" + mSnakeView.getSnakeScore());
			}

			if (intent.getAction().equals("SnakeDeath")) {// ��Ϸ��������
				gameOver();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.snake);
		// // ����1 Android�����Ļ�Ŀ�͸�
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

	private void init() {// ��ʼ������
		// TODO Auto-generated method stub

		/* ========��ȡ��Ļ�ܶ�======== */
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		setting.DENSITY = metric.density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
		Log.i("TAG", "" + metric.density);
		/* ========ע��㲥======== */
		MyRegisterReceiver();

		/* ========�����ߵķ���İ���======== */
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
		/* ========setting��ť======== */
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
		/* =========snake�ռ�======== */
		mSnakeView = (SnakeView) findViewById(R.id.snake);

		/* =========��ʾ����======== */
		mTextViewScore = (TextView) findViewById(R.id.Score);
		mTextViewHighScore = (TextView) findViewById(R.id.HighScore);

		/* =========��ȡsetting��Ϣ======== */
		mPreferences = getSharedPreferences("setting", MODE_PRIVATE);
		setting.MODEL = mPreferences.getInt("setting.MODEL", setting.EASE);
		setting.SNAKE_MOVEMENT_SPEED = mPreferences.getInt(
				"setting.SNAKE_MOVEMENT_SPEED", 100);
		setting.MUSIC_ON_OFF = mPreferences.getBoolean("setting.MUSIC_ON_OFF",
				false);
		setting.WALL_YES_NO = mPreferences.getBoolean("setting.WALL_YES_NO",
				false);
		mScoreManger = new scoreManger(HomeActivity.this);
		/* =========��ʼ����Ϸ״̬======== */
		mButtonAgainGame.setEnabled(setting.SNAKE_IS_LIVING);

		Intent intent = new Intent("com.snake.service.MusicService");// ���ű�������
		intent.addFlags(2);
		startService(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (isTimeLimit()) {
			switch (v.getId()) {
			/* ======�����ߵ��ƶ�����===== */
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
			/* ======setting��ť����===== */
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
	protected void onPause() {// ��ͣ��Ϸ
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
		/* ========�洢���ݿ���Ϣ====== */
		if (setting.SNAKE_IS_LIVING) {
			mSnakeView.saveGame();
		}
		unregisterReceiver(mBroadcastReceiver);// �رչ㲥������
		Intent intent = new Intent("com.snake.service.MusicService");// �رշ���
		stopService(intent);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		Intent intent = new Intent("com.snake.service.MusicService");// ���ű�������
		intent.addFlags(2);
		startService(intent);

	}

	/* ========�Զ��庯��====== */

	private boolean isTimeLimit() {

		oldTime = newTime;
		newTime = System.currentTimeMillis();
		if (oldTime != 0 && (newTime - oldTime < 200))
			return false;

		return true;

	}

	private void MyRegisterReceiver() {// ע��㲥

		IntentFilter filter = new IntentFilter();
		filter.addAction("UpdateScore");
		filter.addAction("SnakeDeath");
		filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);// �������
		filter.addAction(Intent.ACTION_SCREEN_OFF);// ����
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);// ��ҳ��
		HomeActivity.this.registerReceiver(mBroadcastReceiver, filter);
	}

	private void gameOver() {// ��Ϸ�����Ĵ���
		/* ========���յķִ���====== */
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(
				HomeActivity.this);
		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog, null);
		mBuilder.setTitle(R.string.title);
		int rank = mScoreManger.queryRank(mSnakeView.getSnakeScore()) + 1;
		mBuilder.setMessage("��ϲ����Ӣ�۰��" + rank + "����\n����������:");
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
						mScoreBean.setName(name.equals("") ? "����" : name);
						mScoreBean.setScore(mSnakeView.getSnakeScore());
						mScoreManger.insert(mScoreBean);
					}
				});
		// mBuilder.get
		mBuilder.create();// .show();
		Dialog noticeDialog = mBuilder.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
		/* ========��Ϸ��������====== */
		mButtonAgainGame.setEnabled(setting.SNAKE_IS_LIVING);
		mScrollViewSetting.setVisibility(View.VISIBLE);
		mCheckBoxStop.setChecked(mSnakeView.getSnakeIsRunning());
	}
}
