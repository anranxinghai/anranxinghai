package com.anranxinghai.musicplayer.db;


import com.anranxinghai.musicplayer.constant.Constant;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final int VERSION = 1; 
	
	//必须有该构造函数
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseHelper(Context context, String name){
		this(context,name,VERSION);
	}

	public DatabaseHelper(Context context,String name,int version){
		this(context, name, null, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a database");
		db.execSQL("create table "+ Constant.TABLE_NAME +"(id int,music_name varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update a DatabaseQ!");
	}



}
