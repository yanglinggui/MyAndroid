package com.snake.service;

import com.example.mysnakeapp.R;
import com.snake.constant.setting;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

	private MediaPlayer mMediaPlayerEat, mMediaPlayerLose, mMediaPlayerBgm;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mMediaPlayerEat = MediaPlayer
				.create(getApplicationContext(), R.raw.eat);
		mMediaPlayerLose = MediaPlayer.create(getApplicationContext(),
				R.raw.lose);
		mMediaPlayerBgm = MediaPlayer
				.create(getApplicationContext(), R.raw.bgm);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub

		int flag = intent.getFlags();

		if (setting.MUSIC_ON_OFF) {
			switch (flag) {
			case 0:
				mMediaPlayerLose.start();
				break;
			case 1:
				mMediaPlayerEat.start();
				break;
			case 2:
				if (null == mMediaPlayerBgm) {
					mMediaPlayerBgm = MediaPlayer.create(
							getApplicationContext(), R.raw.bgm);
				}
				mMediaPlayerBgm.setLooping(true);
				mMediaPlayerBgm.start();
				break;
			}
		} else {
			if (mMediaPlayerBgm.isPlaying()) {
				mMediaPlayerBgm.pause();
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMediaPlayerEat.release();
		mMediaPlayerLose.release();
		mMediaPlayerBgm.release();
	}
}
