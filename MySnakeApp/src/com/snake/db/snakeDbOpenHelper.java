package com.snake.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class snakeDbOpenHelper extends SQLiteOpenHelper {

	public snakeDbOpenHelper(Context context) {

		super(context, "snakeSnake.db", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table snake(id integer primary key autoincrement,x integer,y integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drod table if exists  snake");
		this.onCreate(db);
	}

}
