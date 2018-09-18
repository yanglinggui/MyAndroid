package com.snake.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snake.bean.Point;

public class snakeManger {

	private ArrayList<Point> snake;

	private snakeDbOpenHelper dbOpenHelper;

	public snakeManger(Context context) {// 构造函数，获取上下文
		dbOpenHelper = new snakeDbOpenHelper(context);
		snake = new ArrayList<Point>();
	}

	public ArrayList<Point> query() {// 数据查询

		SQLiteDatabase db = null;

		Cursor cursor = null;

		Point point = null;

		snake.clear();

		try {
			db = dbOpenHelper.getReadableDatabase();
			cursor = db
					.query("snake", null, null, null, null, null, null, null);

			while (cursor.moveToNext()) {

				point = new Point();

				point.setX(cursor.getInt(cursor.getColumnIndex("x")));
				point.setY(cursor.getInt(cursor.getColumnIndex("y")));

				snake.add(point);
			}

		} catch (Exception e) {

		} finally {
			db.close();
		}

		return snake;
	}

	public void insert(List<Point> snake) {// 数据插入

		SQLiteDatabase db = null;

		ContentValues values;

		try {
			db = dbOpenHelper.getWritableDatabase();

			for (int i = 0; i < snake.size(); i++) {
				values = new ContentValues();
				values.put("x", snake.get(i).getX());
				values.put("y", snake.get(i).getY());
				db.insert("snake", null, values);
			}
		} catch (Exception e) {

		} finally {
			db.close();
		}

	}

	public void delete() {// 删除数据

		SQLiteDatabase db = null;

		try {
			db = dbOpenHelper.getWritableDatabase();
			db.delete("snake", null, null);
		} catch (Exception e) {

		} finally {
			db.close();
		}
	}
}
