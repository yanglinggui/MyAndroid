package com.snake.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snake.bean.scoreBean;

public class scoreManger {

	private ArrayList<scoreBean> scoreList;

	private scoreDbOpenHelper dbOpenHelper;

	public scoreManger(Context context) {// 构造函数，获取上下文
		dbOpenHelper = new scoreDbOpenHelper(context);
		scoreList = new ArrayList<scoreBean>();
	}

	public ArrayList<scoreBean> query() {// 数据查询

		SQLiteDatabase db = null;

		Cursor cursor = null;

		scoreBean bean = null;

		scoreList.clear();

		try {
			db = dbOpenHelper.getReadableDatabase();
			cursor = db.query("score", null, null, null, null, null,
					"score DESC", null);
			int rank = 0;
			int score = -1;
			while (cursor.moveToNext()) {
				bean = new scoreBean();
				if (score != cursor.getInt(cursor.getColumnIndex("score"))) {
					score = cursor.getInt(cursor.getColumnIndex("score"));
					rank++;
				}
				bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bean.setName(cursor.getString(cursor.getColumnIndex("name")));
				bean.setScore(score);
				bean.setRank(rank);
				scoreList.add(bean);
			}

		} catch (Exception e) {

		} finally {
			db.close();
		}
		return scoreList;
	}

	public int queryRank(int score) {// 数据查询

		SQLiteDatabase db = null;

		Cursor cursor = null;

		String where = "score>" + score;

		try {
			db = dbOpenHelper.getReadableDatabase();
			cursor = db.query("score", null, where, null, "score", null,
					"score DESC", null);
			return cursor.getCount();
		} catch (Exception e) {

		} finally {
			db.close();
		}
		return cursor.getCount();
	}

	public void insert(scoreBean bean) {// 数据插入

		SQLiteDatabase db = null;

		ContentValues values = new ContentValues();

		try {
			db = dbOpenHelper.getWritableDatabase();
			values.put("name", bean.getName());
			values.put("score", bean.getScore());
			db.insert("score", null, values);
		} catch (Exception e) {

		} finally {
			db.close();
		}

	}

	public void delete(List<scoreBean> mList) {// 删除

		SQLiteDatabase db = null;

		try {
			db = dbOpenHelper.getWritableDatabase();
			for (scoreBean scoreBean : mList) {
				db.delete("score", "id=" + scoreBean.getId(), null);
			}
		} catch (Exception e) {

		} finally {
			db.close();
		}
	}
}
