package com.snake.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class scoreDbOpenHelper extends SQLiteOpenHelper {

	public scoreDbOpenHelper(Context context) {

		super(context, "scoreSnake.db", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table score(id integer primary key autoincrement,name varchar(10),score integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drod table if exists  score");
		this.onCreate(db);
	}

}
